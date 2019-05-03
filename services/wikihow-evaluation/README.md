# Wikihow Evaluation

## Set up ROUGE
See this link: https://poojithansl7.wordpress.com/2018/08/04/setting-up-rouge/

## Set up PyRouge
See the README here: https://github.com/bheinzerling/pyrouge

## Create Summaries

### With TextRank
- cd ~/Development/git/wikihow-qa/services/wikihow-textrank
- source env/bin/activate
- python3 app/generation.py 
-i ~/Development/git/wikihow-qa/dataset/articles/ 
-o ~/Development/git/wikihow-qa/services/wikihow-evaluation/evaluation_data/textrank

## Run Evaluation
 python3 evaluation.py -m evaluation_data/textrank/model -s evaluation_data/textrank/system