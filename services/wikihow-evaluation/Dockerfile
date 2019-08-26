# docker build -t bigabig/wikihow-eval:latest .
# docker run -p9000:9000 bigabig/wikihow-eval:latest
# visit http://localhost:9000/ to check if it is working
FROM python:3.6-stretch

RUN apt-get update && \
    apt-get install -y git && \
    apt-get install -y perl && \
    apt-get install -y libxml-dom-perl && \
    git clone https://github.com/andersjo/pyrouge.git && \
    git clone https://github.com/bheinzerling/pyrouge.git pyrouge_wrapper

ENV ROUGE_EVAL_HOME=/pyrouge/tools/ROUGE-1.5.5/data/

RUN cd pyrouge/tools/ROUGE-1.5.5/data/WordNet-2.0-Exceptions/ && \
    ./buildExeptionDB.pl . exc WordNet-2.0.exc.db && \
    cd ../ && \
    rm WordNet-2.0.exc.db && \
    ln -s WordNet-2.0-Exceptions/WordNet-2.0.exc.db WordNet-2.0.exc.db

RUN cd /pyrouge_wrapper && \
    python setup.py install && \
    pyrouge_set_rouge_path /pyrouge/tools/ROUGE-1.5.5/

# We copy just the requirements.txt first to leverage Docker cache
COPY app/requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY app /app

EXPOSE 9000

ENTRYPOINT [ "python" ]

CMD [ "app.py" ]
