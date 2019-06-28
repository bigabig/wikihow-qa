# docker build -t bigabig/wikihow-ner:latest .
# docker run -p5000:5000 bigabig/wikihow-ner:latest
# visit http://localhost:5000/ to check if it is working

FROM python:3.6

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt

COPY app.py app.py

RUN python -m spacy download en_core_web_sm

EXPOSE 5000

ENTRYPOINT [ "python" ]

CMD [ "app.py" ]
