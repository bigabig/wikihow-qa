# docker build -t bigabig/wikihow-bertsum:latest .
# docker run -p5000:5000 bigabig/wikihow-bertsum:latest
# visit http://localhost:5000/ 

### 1. Get Linux
FROM ubuntu:18.04

### Change Locale to utf8
RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/* \
    && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
ENV LANG en_US.utf8

### Get Java & Python via the package manager
RUN apt-get update && apt-get install software-properties-common -y && apt-get update && add-apt-repository ppa:openjdk-r/ppa && apt-get update &&  apt-get install -y \
    bash \
    unzip \
    curl \
    openjdk-8-jre \
    python3 \
	python3-pip

### Python Stuff
RUN pip3 install setuptools

COPY app/requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip3 install -r requirements.txt

COPY app /app

COPY corenlp /corenlp

ENV CLASSPATH="/corenlp/stanford-corenlp-3.8.0.jar"

EXPOSE 5000

WORKDIR /app/src

ENTRYPOINT [ "python3" ]

CMD [ "train_new.py" ]
