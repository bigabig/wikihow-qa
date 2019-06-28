import spacy
import json
from flask import Flask
from flask import request
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
nlp = None


@app.route('/')
def hello_world():
    return extract_ner("Tim ist toll. Er arbeitet bei der University of Hamburg.")


@app.route('/ner', methods=['POST'])
def summarize():
    if not request.is_json:
        print("Request is not a JSON object :(")
        return ""
    if nlp is None:
        print("Spacy Model not initialized :(")
        return ""
    content = request.get_json()
    print(content)
    result = extract_ner(content['text'])
    print(result)
    return extract_ner(content['text'])


def extract_ner(text):
    global nlp
    if nlp is None:
        print("Spacy Model not initialized :(")
        return ""

    doc = nlp(text)
    result = json.dumps({
        'entities': [{'text':span.text, 'label':span.label_, 'start':span.start_char, 'end':span.end_char} for span in doc.ents]
    })
    return result


def main():
    # load spacy
    global nlp
    nlp = spacy.load('en_core_web_sm')

    # start flask app
    app.run(host='0.0.0.0')


if __name__ == '__main__':
    main()
