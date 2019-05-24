FROM python:3.6

COPY app/requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY app /app

COPY models /models 

COPY vocab /vocab

EXPOSE 5000

ENTRYPOINT [ "python" ]

CMD [ "app.py" ]
