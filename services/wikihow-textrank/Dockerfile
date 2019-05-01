FROM python:3.6

# We copy just the requirements.txt first to leverage Docker cache
COPY app/requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY app /app

ENTRYPOINT [ "python" ]

CMD [ "app.py" ]