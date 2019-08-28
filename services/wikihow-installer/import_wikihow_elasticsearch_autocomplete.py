import json, sys, io, os
from elasticsearch import Elasticsearch


def main():
    host = os.environ['INSTALLER_ELASTICHOST']
    port = int(float(os.environ['INSTALLER_ELASTICPORT']
    index = os.environ['INSTALLER_ELASTICINDEX']))
    data = os.environ['INSTALLER_DATA']
    timeout = int(float(os.environ['INSTALLER_TIMEOUT']))

    if not host or not port or not index or not data or not timeout:
        print("ENVIRONMENT VARIABLES NOT SET :(")
        return

    # init es
    es = Elasticsearch([host], port)

    # define mapping
    mapping = {
        "mappings": {
            "_doc": {
                "properties": {
                    "article": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    },
                    "filename": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    },
                    "full_article": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    },
                    "summary": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    },
                    "title": {
                        "type": "text",
                        "fields": {
                            "keyword": {
                                "type": "keyword",
                                "ignore_above": 256
                            }
                        }
                    },
                    "suggest_title": {
                        "type": "completion",
                        "max_input_length": 100
                    },
                }
            }
        }
    }

    # create index
    if es.indices.exists(index):
        print("Index '%s' already exists! No need to import data :)" % index)
        return
    print("creating '%s' index..." % index)
    res = es.indices.create(index=index, body=mapping)
    print(" response: '%s'" % res)

    # import data
    id = 0
    dataset_path = os.path.normpath(data)
    for entry in os.scandir(dataset_path):
        filepath = entry.path
        filename = entry.name
        if filename.endswith(".json"):
            print("Indexing " + filename)
            with io.open(filepath, 'r', encoding="utf8") as file:
                json_data = json.load(file)
                json_data['filename'] = filename
                json_data['suggest_title'] = json_data['title']
                es.index(index=index, doc_type="_doc", id=id, body=json_data)
                id = id + 1
        else:
            continue


if __name__ == "__main__":
    main()
