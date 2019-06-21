import io
import os
from argparse import ArgumentParser

# This script can convert the daily news and cnn dataset into csv files!


def main():
    parser = ArgumentParser()
    parser.add_argument("-d", "--data", dest="data", help="path to dataset", metavar="DATA")
    args = parser.parse_args()

    if not args.data:
        parser.print_help()
        return

    data = open("data.csv", "a", encoding="utf8")
    data.write("article,summary")
    dataset_path = os.path.normpath(args.data)
    for entry in os.scandir(dataset_path):
        filepath = entry.path
        filename = entry.name
        if filename.endswith(".story"):
            print("Processing " + filename)
            with io.open(filepath, 'r', encoding="utf8") as file:
                text = ""
                summary = ""
                processing_text = True
                for line in file:
                    processed_line = preprocess_text(line)
                    if processed_line == "@highlight":
                        processing_text = False
                        continue
                    elif processed_line != "":
                        if processing_text:
                            text += " " + processed_line
                        else:
                            processed_line = maybe_add_dot(processed_line)
                            summary += " " + processed_line

                data.write("\n\""+text.strip()+"\",\""+summary.strip()+"\"")
        else:
            continue

    data.close()


def preprocess_text(text):
    text = text.strip()
    text = text.replace("\n", "")
    text = text.replace("\t", " ")
    text = text.replace("\"", "\"\"")
    return text


def maybe_add_dot(text):
    if not text.endswith("."):
        text = text + "."
    return text


if __name__ == "__main__":
    main()
