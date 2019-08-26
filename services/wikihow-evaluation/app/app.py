import json
import os

from flask import Flask
from flask import request
from pyrouge import Rouge155

app = Flask(__name__)

model_path = os.path.normpath("/app/model/")
system_path = os.path.normpath("/app/system/")


@app.route('/')
def hello_world():
    output, output_dict = analyze("Tim ist gut.", "Tim ist toll.")
    print(output)

    result = json.dumps({
        'evaluation': output_dict,
        'formatted': output
    })
    return result


@app.route('/evaluate', methods=['POST'])
def evaluate():
    if not request.is_json:
        print("Request is not a JSON object :(")
        return ""
    content = request.get_json()
    print(content)

    output, output_dict = analyze(content['summary'], content['gold'])
    print(output)

    result = json.dumps({
        'evaluation': output_dict,
        'formatted': output
    })
    return result


def analyze(summary, gold):
    r = Rouge155()
    r.system_dir = system_path
    r.model_dir = model_path
    r.system_filename_pattern = '(\w+).txt'
    r.model_filename_pattern = '#ID#.txt'

    f = open(model_path + "/file.txt", "w")
    f.write(summary)
    f.close()

    f = open(system_path + "/file.txt", "w")
    f.write(gold)
    f.close()

    output = r.convert_and_evaluate()
    output_dict = r.output_to_dict(output)

    return output, output_dict


def main():
    # init rouge
    if not os.path.exists(model_path):
        os.makedirs(model_path)

    if not os.path.exists(system_path):
        os.makedirs(system_path)

    # start flask app
    app.run(host='0.0.0.0', port=9000)


if __name__ == '__main__':
    main()

