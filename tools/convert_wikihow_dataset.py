import json, io, os, re
from argparse import ArgumentParser


def main():
    parser = ArgumentParser()
    parser.add_argument("-d", "--data", dest="data", help="path to dataset", metavar="DATA")
    parser.add_argument("-s", "--split", dest="split", help="put every k file into test data. For example -s 10",
                        metavar="SPLIT")
    args = parser.parse_args()

    if not args.data:
        parser.print_help()
        return

    split = 0
    if args.split:
        split = int(float(args.split))
        test_texts = open("test_texts.txt", "a", encoding="utf8")
        test_summaries = open("test_summaries.txt", "a", encoding="utf8")

    train_texts = open("train_texts.txt", "a", encoding="utf8")
    train_summaries = open("train_summaries.txt", "a", encoding="utf8")

    counter = 0
    dataset_path = os.path.normpath(args.data)
    for entry in os.scandir(dataset_path):
        filepath = entry.path
        filename = entry.name
        if filename.endswith(".json"):
            print("Processing " + filename)
            counter = counter + 1
            with io.open(filepath, 'r', encoding="utf8") as file:
                json_data = json.load(file)
                text = preprocess_text(json_data['article'])
                summary = preprocess_text(json_data['summary_string'])

                if split != 0 and counter % split == 0:
                    test_texts.write(text + "\n")
                    test_summaries.write(summary + "\n")
                else:
                    train_texts.write(text + "\n")
                    train_summaries.write(summary + "\n")
        else:
            continue

    train_texts.close()
    train_summaries.close()

    if args.split:
        test_texts.close()
        test_summaries.close()


def preprocess_text(text):
    text = text.strip()
    text = text.replace("\n", "")
    text = re.sub(r"\.+\s*,+\s* ", ". ", text)
    return text


if __name__ == "__main__":
    main()
