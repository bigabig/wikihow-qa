FROM python:3.6

# We copy just the requirements.txt first to leverage Docker cache

COPY requirements.txt requirements.txt

COPY wait_to_start.sh wait_to_start.sh

RUN pip install -r requirements.txt

COPY import_wikihow_elasticsearch_autocomplete.py import_wikihow_elasticsearch_autocomplete.py

EXPOSE 5000

CMD [ "wait_to_start.sh" ]
