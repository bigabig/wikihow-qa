# Frontend
This is the frontend of the wikihow project. It provides a user interface to ask questions and summarize provided text. 
Moreover, it features an analysis component where it is possible to compare and analyze different summarization techniques.
Also, there is an evaluation component that lists various statistics about the different summarization techniques.

## Building the frontend
This is a short instruction on building the frontend. This is a necessary step before building the backend / server.
- Change into correct diretory: cd wikihow-qa/frontend
- Install dependencies: npm install
- Build the project: npm run build
- Clear server/backend: rm -r ../backend/src/main/resources/public/*
- Copy the build to the server/backend: mv dist/* ../backend/src/main/resources/public/

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Run your tests
```
npm run test
```

### Lints and fixes files
```
npm run lint
```
