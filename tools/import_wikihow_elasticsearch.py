import json,sys,io,os
from argparse import ArgumentParser
from elasticsearch import Elasticsearch
from argparse import ArgumentParser

def main():
	parser = ArgumentParser()
	parser.add_argument("-d", "--data", dest="data", help="path to dataset", metavar="DATA")
	parser.add_argument("-i", "--index", dest="index", help="elasticsearch index", metavar="INDEX")	
	args = parser.parse_args()

	if not args.data or not args.index:
		parser.print_help()
		return

	es = Elasticsearch(['localhost'], port=9200)

	id = 0
	dataset_path = os.path.normpath(args.data);
	for entry in os.scandir(dataset_path):
		filepath = entry.path
		filename = entry.name
		if filename.endswith(".json"):
			print("Indexing "+filename)
			with io.open(filepath, 'r', encoding="utf8") as file:
				json_data = json.load(file)
				json_data['filename'] = filename
				es.index(index=args.index, doc_type="_doc", id=id, body=json_data)
				id = id + 1
		else:
			continue

if __name__ == "__main__":
	main()

