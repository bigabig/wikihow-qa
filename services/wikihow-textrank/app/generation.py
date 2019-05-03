import os, io, json
import textrank as textrank
from argparse import ArgumentParser


def main():
    # parse arguments
    parser = ArgumentParser()
    parser.add_argument("-i", "--in", dest="input", help="path to directory containing wikihow articles in json format", metavar="IN")
    parser.add_argument("-o", "--out", dest="output", help="path to directory to save summaries", metavar="OUT")
    args = parser.parse_args()
    if not args.input or not args.output:
        parser.print_help()
        return

    input_path = os.path.normpath(args.input)
    output_path = os.path.normpath(args.output)
    model_path = os.path.normpath(output_path+"/model/")
    system_path = os.path.normpath(output_path+"/system/")

    if not os.path.exists(model_path):
        os.makedirs(model_path)
    if not os.path.exists(system_path):
        os.makedirs(system_path)

    # loop through every input file
    count = 0
    for entry in os.scandir(input_path):
        if count >= 10:
            break
        count = count + 1

        filepath = entry.path
        filename: str = entry.name
        new_filename = filename.replace(".json", ".txt")
        if filename.endswith(".json"):
            print("Reading file " + filename)

            # read input file as json object
            with io.open(filepath, "r", encoding="utf-8") as json_file:
                data = json.load(json_file)
                print(data['article'])

                # write gold standard summary
                with open(model_path+"/"+new_filename, 'w') as gold_summary:
                    gold_summary.write("".join(data['summary']))

                # create summary
                summary = textrank.generate_summary(data['article'], 3)

                # write textrank summary
                with open(system_path+"/"+new_filename, 'w') as system_summary:
                    system_summary.write(summary)

        else:
            continue


if __name__ == '__main__':
    main()