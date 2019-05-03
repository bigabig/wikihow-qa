import json,sys,io,os,re
from argparse import ArgumentParser
from pyrouge import Rouge155


def main():
    # parse arguments
    parser = ArgumentParser()
    parser.add_argument("-m", "--model", dest="model", help="path to directory containing gold standard summaries", metavar="MODEL")
    parser.add_argument("-s", "--system", dest="system", help="path to directory containing your summaries", metavar="SYSTEM")
    args = parser.parse_args()
    if not args.model or not args.system:
        parser.print_help()
        return

    model_path = os.path.normpath(args.model)
    system_path = os.path.normpath(args.system)

    # set up rouge
    r = Rouge155()
    r.system_dir = system_path
    r.model_dir = model_path
    r.system_filename_pattern = '(\w+).txt'
    r.model_filename_pattern = '#ID#.txt'

    output = r.convert_and_evaluate()
    print(output)
    output_dict = r.output_to_dict(output)
    print(output_dict)


if __name__ == "__main__":
    main()