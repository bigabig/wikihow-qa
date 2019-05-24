import run_summarization
from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/')
def hello_world():
    abstract = "tim ist toll"
    article = "They usually cost several thousands of dollars. It would be a wise idea to check with Humanware for pricing details or financial support if you need it.;, If you've never used one before, take a while to explore it and its layout. You will know it is placed correctly in front of you if you feel (1) the flap of the BrailleNote case is away from your body and (2) you feel the refreshable Braille display is closest to you. Open it, and you should quickly notice a rocker switch, along with a couple of buttons and a headphone jack that won't be discussed here. Check the Braille display. Your BrailleNote should now be on. Main menu is displayed on the display. You are now ready to explore even further., This is what allows you to type and create documents in a variety of formats, along with printing, embossing, and e-mailing them to family, friends, and colleagues., The rest is pretty self-explanatory. Press space through the keyword menu until your desired task is displayed. Then type u for the user guide. Now press t (for table of contents) or i (for index)."
    return run_summarization.do_decode(abstract, article)


@app.route('/summarize', methods=['POST'])
def summarize():
    if not request.is_json:
        print("Request is not a JSON object :(")
        return ""
    content = request.get_json()
    print(content)
    return run_summarization.do_decode(content['abstract'], content['article'])


def main():
    # start flask app
    run_summarization.main()
    app.run(host='0.0.0.0')


if __name__ == '__main__':
    main()

