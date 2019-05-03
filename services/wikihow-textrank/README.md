## Setup
- Create virtual environment
  - python3 -m venv env
- Activate virtual environment
  - source env/bin/activate
- Install dependencies
  - pip3 install -r app/requirements.txt
- Deactivate virtual environment
  - deactivate
  
##Run Flask Microservice
- Start the server
  - python3 app/app.py
- Check if it works: visit http://localhost:5000

##Bulk create summaries
- You need to know location of wikihow articles as well as output dir
  - python3 app/generation.py 
    -i ~/Development/git/wikihow-qa/dataset/articles/ 
    -o ~/Development/git/wikihow-qa/services/wikihow-evaluation/evaluation_data/textrank
- Wait until finished
