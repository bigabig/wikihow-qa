from flask import Flask
from flask import request
import textrank as textrank

app = Flask(__name__)


@app.route('/')
def hello_world():
    return textrank.generate_summary("Tim is a good boy. Everyone likes tim. Tim is a bad boy. Tim is a great boy.", 1)


@app.route('/summarize', methods=['POST'])
def summarize():
    if not request.is_json:
        print("Request is not a JSON object :(")
        return ""
    content = request.get_json()
    print(content)
    return textrank.generate_summary(content['text'], content['length'])


def main():
    # start flask app
    app.run(host='0.0.0.0')


if __name__ == '__main__':
    main()

