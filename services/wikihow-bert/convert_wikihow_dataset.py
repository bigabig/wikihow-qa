import json, io, os, re
from argparse import ArgumentParser


def main():
    parser = ArgumentParser()
    parser.add_argument("-d", "--data", dest="data", help="path to dataset", metavar="DATA")
    args = parser.parse_args()

    if not args.data:
        parser.print_help()
        return

    data = open("data.csv", "a", encoding="utf8")
    data.write("article\tsummary")
    dataset_path = os.path.normpath(args.data)
    for entry in os.scandir(dataset_path):
        filepath = entry.path
        filename = entry.name
        if filename.endswith(".json"):
            print("Processing " + filename)
            with io.open(filepath, 'r', encoding="utf8") as file:
                json_data = json.load(file)
                text = preprocess_text(json_data['article'])
                summary = preprocess_text(json_data['summary_string'])
                data.write("\n\""+text+"\"\t\""+summary+"\"")
        else:
            continue

    data.close()


def preprocess_text(text):
    text = text.strip()
    text = text.replace("\n", "")
    text = text.replace("\t", " ")
    text = text.replace("\"", "\\\"")
    text = re.sub(r"\.+\s*,+\s* ", ". ", text)
    return text


if __name__ == "__main__":
    main()
