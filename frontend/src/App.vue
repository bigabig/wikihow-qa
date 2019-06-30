<template>
  <div id="app">
    <div class="w3-theme-l5">
      <!-- Navbar -->
      <div class="w3-top">
        <div class="w3-bar w3-theme w3-left-align w3-large">
          <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
          <a href="#" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Logo</a>
          <a v-on:click="currentPage = 0" href="#" v-bind:class="{ 'w3-theme-d4': currentPage === 0 }" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i> Eval</a>
          <a v-on:click="currentPage = 1" href="#" v-bind:class="{ 'w3-theme-d4': currentPage === 1 }" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i> Eval</a>
          <a v-on:click="currentPage = 2" href="#" v-bind:class="{ 'w3-theme-d4': currentPage === 2 }" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i> Eval</a>
          <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-user"></i></a>
          <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-envelope"></i></a>
          <div class="w3-dropdown-hover w3-hide-small">
            <button class="w3-button w3-padding-large" title="Notifications"><i class="fa fa-bell"></i><span class="w3-badge w3-right w3-small w3-green">3</span></button>
            <div class="w3-dropdown-content w3-card-4 w3-bar-block" style="width:300px">
              <a href="#" class="w3-bar-item w3-button">One new friend request</a>
              <a href="#" class="w3-bar-item w3-button">John Doe posted on your wall</a>
              <a href="#" class="w3-bar-item w3-button">Jane likes your post</a>
            </div>
          </div>
          <a href="#" class="w3-bar-item w3-button w3-hide-small w3-right w3-padding-large w3-hover-white" title="My Account">
            <img src="/w3images/avatar2.png" class="w3-circle" style="height:23px;width:23px" alt="Avatar">
          </a>
        </div>
      </div>

      <!-- Navbar on small screens -->
      <div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
        <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 1</a>
        <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 2</a>
        <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 3</a>
        <a href="#" class="w3-bar-item w3-button w3-padding-large">My Profile</a>
      </div>

      <!-- Page Container -->
      <div v-if="currentPage === 0" class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
        <!-- The Grid -->
        <div class="w3-row">

          <!-- Middle Column -->
          <div class="w3-col">

            <div class="w3-row-padding">
              <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-bar w3-theme-l3">
                    <button v-on:click="evalMode = 0" v-bind:class="{ 'w3-theme-l1': evalMode === 0 }" class="w3-bar-item w3-button w3-hover-white">Ask a question</button>
                    <button v-on:click="evalMode = 1" v-bind:class="{ 'w3-theme-l1': evalMode === 1 }" class="w3-bar-item w3-button w3-hover-white">Summarize your text</button>
                  </div>
                  <div class="w3-container w3-padding">
                    <h6 v-if="evalMode === 0" class="w3-opacity">Type your question:</h6>
                    <h6 v-if="evalMode === 1" class="w3-opacity">Provide Text to summarize:</h6>
                    <p v-if="evalMode === 0"><input v-model="question" class="w3-border w3-padding" type="text" style="width:100%"></p>
                    <p v-if="evalMode === 1"><textarea v-model="text" class="w3-border w3-padding" rows="5" style="width:100%"></textarea></p>
                    <p><select v-model="method" class="w3-select w3-border w3-padding w3-white" name="option">
                      <option v-if="evalMode === 0" value="gold">Cheating</option>
                      <option value="textrank">TextRank</option>
                      <option value="network">Pointer-Generator Network</option>
                      <option value="bertsum">BertSum</option>
                    </select></p>
                    <button v-if="evalMode === 0" v-on:click="answerQuestion" type="button" class="w3-button w3-theme"><i class="fa fa-search"></i>  Search!</button>
                    <button v-if="evalMode === 1" v-on:click="answerText" type="button" class="w3-button w3-theme"><i class="fa fa-search"></i>  Summarize!</button>
                  </div>
                </div>
              </div>
            </div>

            <template v-if="evalMode === 0">
              <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity">generated by {{ method }}</span>
                <h4>Short Instructions</h4>
                <hr class="w3-clear">
                <ul>
                  <li v-for="sentence in articleSummaries[method]">
                    {{ sentence }}
                  </li>
                </ul>
                <button type="button" class="w3-button w3-theme-d1 w3-margin-bottom"><i class="fa fa-thumbs-up"></i>  Like</button>
                <button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>  Comment</button>
              </div>

              <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity">a WikiHow Article</span>
                <h4>Detailed Instructions</h4>
                <hr class="w3-clear">
                <p>{{ wikihowArticle.article}}</p>
              </div>
            </template>

            <template v-if="evalMode === 1">
              <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity">generated by {{ method }}</span>
                <h4>Summary</h4>
                <hr class="w3-clear">
                <ul>
                  <li v-for="sentence in textSummaries[method]">
                    {{ sentence }}
                  </li>
                </ul>
              </div>
            </template>

            <!-- End Middle Column -->
          </div>

          <!-- End Grid -->
        </div>

        <!-- End Page Container -->
      </div>

      <div v-if="currentPage === 1" class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
        <!-- Input -->
        <div class="w3-row">

          <!-- Search Column -->
          <div class="w3-col">

            <div class="w3-row-padding">
              <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-bar w3-theme-l3">
                    <button v-on:click="evalMode = 0" v-bind:class="{ 'w3-theme-l1': evalMode === 0 }" class="w3-bar-item w3-button w3-hover-white">Analyze a question</button>
                    <button v-on:click="evalMode = 1" v-bind:class="{ 'w3-theme-l1': evalMode === 1 }" class="w3-bar-item w3-button w3-hover-white">Analyze your own text</button>
                  </div>
                  <div class="w3-container w3-padding">
                    <h6 v-if="evalMode === 0" class="w3-opacity">Type your question:</h6>
                    <h6 v-if="evalMode === 1" class="w3-opacity">Provide Text to summarize:</h6>
                    <p v-if="evalMode === 0"><input v-model="question" class="w3-border w3-padding" type="text" style="width:100%"></p>
                    <p v-if="evalMode === 1"><textarea v-model="text" id="textarea-eval" class="w3-border w3-padding" rows="5" style="width:100%"></textarea></p>
                    <p><select v-model="method" class="w3-select w3-border w3-padding w3-white" name="option">
                      <option value="all" selected>All Methods</option>
                      <option v-if="evalMode === 0" value="gold">Cheating</option>
                      <option value="textrank">TextRank</option>
                      <option value="network">Pointer-Generator Network</option>
                      <option value="bertsum">BertSum</option>
                    </select></p>
                    <p>
                      <template v-for="entity in allEntities">
                        <input v-on:change="updateHighlights" type="checkbox" :id="entity" :value="entity" v-model="checkedEntities"><label :for="entity"><span :class="entity" class="NER-NO-REMOVE NER">{{entity}}</span></label>
                      </template>
                    </p>
                    <button v-if="evalMode === 0" v-on:click="evaluateQuestion" id="button-evalquestion" type="button" class="w3-button w3-theme"><i class="fa fa-search"></i>  Analyze!</button>
                    <button v-if="evalMode === 1" v-on:click="evaluateText" id="button-evaltext" type="button" class="w3-button w3-theme"><i class="fa fa-search"></i>  Analyze!</button>
                  </div>
                </div>
              </div>
            </div>

            <!-- End Search Column -->
          </div>

          <!-- End Input -->
        </div>

        <!-- Evaluation-->
        <div class="w3-row">

          <!-- Left Column -->
          <div class="w3-col m6">

            <div v-for="summarizationMethod in allMethods" class="w3-row-padding wikihow-padding-top">
              <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-container w3-padding">
                    <h6 class="w3-opacity">Summary {{summarizationMethod}}</h6>
                    <ul>
                      <li v-if="evalMode === 0" v-for="sentence in articleSummaries[summarizationMethod]" v-html="sentence"></li>
                      <li v-if="evalMode === 1" v-for="sentence in textSummaries[summarizationMethod]" v-html="sentence"></li>
                    </ul>
                    <p style="font-size:18px; margin-bottom: 0;">
                      <span class="w3-opacity">Submit a rating: </span>
                      <span v-on:click="rateMethod(summarizationMethod, 1)" @mouseover="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?1:hover[summarizationMethod]" @mouseleave="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?0:hover[summarizationMethod]" :class="{ checked: hover[summarizationMethod] >= 1 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(summarizationMethod, 2)" @mouseover="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?2:hover[summarizationMethod]" @mouseleave="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?0:hover[summarizationMethod]" :class="{ checked: hover[summarizationMethod] >= 2 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(summarizationMethod, 3)" @mouseover="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?3:hover[summarizationMethod]" @mouseleave="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?0:hover[summarizationMethod]" :class="{ checked: hover[summarizationMethod] >= 3 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(summarizationMethod, 4)" @mouseover="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?4:hover[summarizationMethod]" @mouseleave="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?0:hover[summarizationMethod]" :class="{ checked: hover[summarizationMethod] >= 4 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(summarizationMethod, 5)" @mouseover="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?5:hover[summarizationMethod]" @mouseleave="hover[summarizationMethod]=ratingAllowed[summarizationMethod]?0:hover[summarizationMethod]" :class="{ checked: hover[summarizationMethod] >= 5 }" class="fa fa-star"></span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div v-if="evalMode === 0" class="w3-row-padding wikihow-padding-top">
              <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-container w3-padding">
                    <h6 class="w3-opacity">Summary gold</h6>
                    <ul>
                      <li v-for="sentence in wikihowArticle.summary" v-html="sentence"></li>
                    </ul>
                    <p style="font-size:18px; margin-bottom: 0;">
                      <span class="w3-opacity">Submit a rating: </span>
                      <span v-on:click="rateMethod('gold', 1)" @mouseover="hover['gold']=ratingAllowed['gold']?1:hover['gold']" @mouseleave="hover['gold']=ratingAllowed['gold']?0:hover['gold']" :class="{ checked: hover['gold'] >= 1 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod('gold', 2)" @mouseover="hover['gold']=ratingAllowed['gold']?2:hover['gold']" @mouseleave="hover['gold']=ratingAllowed['gold']?0:hover['gold']" :class="{ checked: hover['gold'] >= 2 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod('gold', 3)" @mouseover="hover['gold']=ratingAllowed['gold']?3:hover['gold']" @mouseleave="hover['gold']=ratingAllowed['gold']?0:hover['gold']" :class="{ checked: hover['gold'] >= 3 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod('gold', 4)" @mouseover="hover['gold']=ratingAllowed['gold']?4:hover['gold']" @mouseleave="hover['gold']=ratingAllowed['gold']?0:hover['gold']" :class="{ checked: hover['gold'] >= 4 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod('gold', 5)" @mouseover="hover['gold']=ratingAllowed['gold']?5:hover['gold']" @mouseleave="hover['gold']=ratingAllowed['gold']?0:hover['gold']" :class="{ checked: hover['gold'] >= 5 }" class="fa fa-star"></span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!-- Right Column -->
          <div class="w3-col m6">
            <div v-if="evalMode === 0" class="w3-container w3-card w3-white w3-round w3-margin"><br>
              <span class="w3-right w3-opacity">a WikiHow Article</span>
              <h4>Original Article</h4>
              <hr class="w3-clear">
              <p v-html="evalArticle"></p>
            </div>
            <div v-if="evalMode === 1" class="w3-container w3-card w3-white w3-round w3-margin"><br>
              <span class="w3-right w3-opacity">a User Input Text</span>
              <h4>Original Text</h4>
              <hr class="w3-clear">
              <p v-html="evalText"></p>
            </div>
          </div>

        </div>

        <!-- End Page Container -->
      </div>

      <div v-if="currentPage === 2" class="w3-container w3-content" style="max-width:1400px;margin-top:80px">
        <!-- The Grid -->
        <div class="w3-row">

          <!-- Middle Column -->
          <div class="w3-col">

            <div v-for="summarizationMethod in allMethods" class="w3-row-padding wikihow-padding-top">
              <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-container w3-padding">
                    <h4 class="w3-opacity">Evaluation of Method "{{summarizationMethod}}"</h4>
                    <line-chart :chart-data="chartData[summarizationMethod]" :options="chartOptions"></line-chart>
                    <button @click="fillData(summarizationMethod)">Refresh</button>
                  </div>
                </div>
              </div>
            </div>

            <!-- End Middle Column -->
          </div>

          <!-- End Grid -->
        </div>

        <!-- End Page Container -->
      </div>

      <br>

      <!-- Footer -->
      <footer class="w3-container w3-theme-d3 w3-padding-16">
        <h5>Footer</h5>
      </footer>
      <footer class="w3-container w3-theme-d5">
        <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
      </footer>
    </div>
  </div>
</template>

<script>
import LineChart from './components/LineChart';

require('@/assets/css/main.css');

export default {
  name: 'app',
  components: {
    LineChart,
  },
  data: function () {
    return {
      question: "how to ...",
      oldQuestion: "string",
      wikihowArticle: {
        "article": "string",
        "filename": "string",
        "score": 0,
        "summary": [
          "string",
        ],
        "summary_string": "string",
        "title": "string"
      },
      method: "textrank",
      oldMethod: "string",
      allMethods: ["textrank", "network"],
      currentPage: 0,       // the actual body to show
      evalMode: 0,          // analyze question or own text?
      text: "string",
      oldText: "string",
      evalText: "string",
      evalArticle: "string",
      articleSummaries: {
        "gold": [
          "string",
        ],
        "textrank": [
          "string",
        ],
        "network": [
          "string",
        ],
        "bertsum": [
          "string",
        ],
      },
      textSummaries: {
        "gold": [
          "string",
        ],
        "textrank": [
          "string",
        ],
        "network": [
          "string",
        ],
        "bertsum": [
          "string",
        ],
      },
      entities: [
        {
          "end": 0,
          "label": "string",
          "start": 0,
          "text": "string"
        }
      ],
      keywords: [
        {
          "score": 0,
          "word": "string"
        }
      ],
      allEntities: [
        "PERSON",
        "LOC",
        "ORG",
        "MISC",
        "DATE",
        "TIME",
        "CARDINAL",
        "ORDINAL",
        "LAW",
        "MONEY",
        "PRODUCT",
        "GPE",
        "EVENT",
        "NORP",
        "FAC"
      ],
      checkedEntities: [
        "PERSON"
      ],
      hover: {
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      },
      ratingAllowed: {
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      },
      chartData: {
        "gold": null,
        "textrank": null,
        "network": null,
        "bertsum": null,
      },
      chartOptions: {
        responsive: false,
        scales: {
          xAxes: [{
            ticks: {
              beginAtZero: true,
              stepSize: 1,
            },
          }]
        }
      }
    }
  },
  watch: {
    currentPage: function (val) {
      console.log("Current Page changed! Page is " + val);

      // when page changes, update highlighted entities!
      setTimeout(() => {this.updateHighlights();}, 1);

      // when page changes and method is all, change method to gold
      if(val === 0 && this.method === "all") {
        this.method = 'gold';
      }

      if(val === 2) {
        for(let method of this.allMethods) {
          this.updateCharts(method);
        }
      }
    },
    evalMode: function (val) {
      console.log("Eval Mode changed! Mode is " + val);

      // when eval mode changes, update highlighted entities!
      setTimeout(() => {this.updateHighlights();}, 1);

      // when eval mode changes to text, and method is gold switch to textrank
      if(val === 1 && this.method === "gold") {
        this.method = 'textrank';
      }
    }
  },
  methods: {
    updateHighlights: function() {
      // loop through all entities
      this.allEntities.forEach(entity => {
        // if entity is checked
        if(this.checkedEntities.indexOf(entity) >= 0) {
          Array.from(document.getElementsByClassName(entity)).forEach(element => {
            // activate visualization
            if(!element.classList.contains("NER")) {
              element.classList.add("NER");
            }
          });
        // if entity is not checked
        } else {
          Array.from(document.getElementsByClassName(entity)).forEach(element => {
            // deactivate visualization
            if(element.classList.contains("NER") && !element.classList.contains("NER-NO-REMOVE")) {
              element.classList.remove("NER");
            }
          });
        }
      });
    },
    fetchWikihowArticle: async function() {
      // get article from wikidata
      console.log("Fetching new WikiHow Artice");
      let data = await postData('http://localhost:8080/wikihowqa/articles', {
        "count": 1,
        "elasticindex": "wikihow",
        "topic": this.question
      });

      console.log("Success fetching new WikiHow Article");
      if(data.articles !== null && data.articles.length > 0) {
        this.wikihowArticle = data.articles[0];
        this.articleSummaries.gold = this.wikihowArticle.summary;
        return data.articles[0];
      } else {
        throw new Error("WikiHow Article is empty!");
      }
    },
    fetchSummary: async function(text, method) {
      // get summary from the certain method
      console.log("Fetching new Summary with method " + method);
      let data = await postData('http://localhost:8080/wikihowqa/summarize', {
        "method": method,
        "text": text
      });

      console.log("Success fetching new Summary");
      console.log(data);
      if(data.summary !== undefined && data.summary !== null && data.summary.length > 5) {
        return data.summary;
      } else {
        throw new Error("Summary is empty!");
      }
    },
    fetchEntities: async function(text) {
      // get entities from text
      console.log("Fetching new Entities");
      let data = await postData('http://localhost:8080/wikihowqa/ner', {
        "text": text
      });

      console.log("Success fetching entities");
      if(data.entities !== null && data.entities.length > 0) {
        this.entities = data.entities;
        return data.entities;
      } else {
        throw new Error("Entities are empty!");
      }
    },
    fetchKeywords: async function(text) {
      // get entities from text
      console.log("Fetching Keywords");
      let data = await postData('http://localhost:8080/wikihowqa/keywords', {
        "count": 5,
        "lang": "eng",
        "text": text
      });

      console.log("Success fetching keywords");
      if(data.keywords !== null && data.keywords.length > 0) {
        this.keywords = data.keywords;
        return data.keywords;
      } else {
        throw new Error("Keywords are empty!");
      }
    },
    fetchRatings: async function(method) {
      console.log("Fetching Ratings for methd" + method);
      let data = await getData('http://localhost:8080/wikihowqa/ratings?method='+method);
      return data;
    },
    rateMethod: async function(method, rating) {
      if(!this.ratingAllowed[method])
        return;

      console.log("Rating Method " + method + " with " + rating + " stars");
      this.ratingAllowed[method] = false;
      let message = await postData('http://localhost:8080/wikihowqa/rate', {
        "method": method,
        "rating": rating,
        "title": this.wikihowArticle.title
      });
      console.log(message);
    },
    answerQuestion: async function() {
      // first fetch the wikihow article
      let article = undefined;
      try {
        article = await this.fetchWikihowArticle();
        this.wikihowArticle = article;
        this.articleSummaries.gold = article.summary;
      } catch(error) {
        console.log("An error occured during fetching the WikiHow article");
        console.log(error);
        return;
      }

      // summarize article depending on the selected method
      try {
        let summary = await this.fetchSummary(article.article, this.method);
        this.articleSummaries[this.method] = [summary];
      } catch(error) {
        console.log("An error occured during fetching the summary for the WikiHow article with the method " + this.method);
        console.log(error);
      }
    },
    answerText: async function() {
      // get input from textarea
      let input = this.text;

      if(this.evalText !== undefined && this.evalText !== null && this.evalText.length > 5) {
        // summarize article depending on the selected method
        try {
          let summary = await this.fetchSummary(input, this.method);
          this.textSummaries[this.method] = [summary];
        } catch(error) {
          console.log("An error occured during fetching the summary for the WikiHow article with the method " + this.method);
          console.log(error);
        }
      }
    },
    evaluateQuestion: async function() {
      // first fetch the wikihow article
      let article = undefined;
      try {
        article = await this.fetchWikihowArticle();
        this.evalArticle = article.article;
      } catch(error) {
        console.log("An error occured during fetching the WikiHow article");
        console.log(error);
        return;
      }

      console.log("Processing WikiHow article");

      // Get Named Entities, Get Keywords, Then visualize them
      this.evalArticle = await this.processEntitiesAndKeywords(article.article);

      // summarize article
      // depending on selection, use all methods
      if(this.method === 'all') {
        for(let method of this.allMethods) {
          // Get Summary
          let summary = await this.generateSummary(article.article, method);
          this.articleSummaries[method] = [summary];
        }
      // or just the selected method
      } else {
        // Get Summary
        let summary = await this.generateSummary(article.article, this.method);
        this.articleSummaries[this.method] = [summary];
      }
    },
    evaluateText: async function() {
      // get input from textarea
      let input = this.text;
      this.evalText = input;

      // then process it
      if(this.evalText !== undefined && this.evalText !== null && this.evalText.length > 5) {
        console.log("Processing text");

        // Get Named Entities, Get Keywords, Then visualize them
        this.evalText = await this.processEntitiesAndKeywords(input);

        // summarize input
        // depending on selection, use all methods
        if(this.method === 'all') {
          for(let method of this.allMethods) {
            let summary = await this.generateSummary(input, method);
            this.textSummaries[method] = [summary];
          }
          // or just the selected method
        } else {
          let summary = await this.generateSummary(input, this.method);
          this.textSummaries[this.method] = [summary];
        }
      }
    },
    hasQuestionChanged() {
      if(this.oldQuestion !== this.question) {
        this.oldQuestion = this.question;
        return true;
      }
      return false;
    },
    hasTextChanged() {
      if(this.oldText !== this.text) {
        this.oldText = this.text;
        return true;
      }
      return false;
    },
    hasMethodChanged() {
      if(this.oldMethod !== this.method) {
        this.oldMethod = this.method;
        return true;
      }
      return false;
    },
    visualizeEntitiesAndKeywords: function(text, entities, keywords) {
      console.log("Visualizing Entities and Keywords");
      if(entities === undefined && keywords === undefined) {
        return;
      }

      console.log(entities);
      console.log(keywords);

      // Collect all text replacements
      let replacements = [];

      if(entities !== undefined) {
        entities.forEach((element) => {
          let spanClass = this.checkedEntities.indexOf(element.label) >= 0 ? "NER " + element.label : element.label;
          let spanStart = "<span class='"+ spanClass + "'>";
          let spanEnd = " <b class='NER-LABEL'>"+element.label+"</b></span>";

          replacements.push([element.start, spanStart]);
          replacements.push([element.end, spanEnd]);
        });
      }

      if(keywords !== undefined) {
        keywords.forEach((element) => {
          // if(!options[element[1]])
          //   return;

          let match;
          let re = new RegExp(element.word,"g");
          while ((match = re.exec(text)) != null) {
            let spanStart = "<span class='KEYWORD'>";
            let spanEnd = "</span>";

            replacements.push([match.index, spanStart]);
            replacements.push([match.index + element.word.length, spanEnd]);
          }
        });
      }

      // Sort the replacements
      replacements = replacements.sort((a, b) => a[0] - b[0]);

      // Insert the replacements
      let newText = text;
      let offset = 0;
      replacements.forEach(function (element) {
        newText = text.slice(0, element[0] + offset) + element[1] + text.slice(element[0] + offset, text.length);
        text = newText;
        offset = offset + element[1].length;
      });

      return text;
    },
    processEntitiesAndKeywords: async function(text) {
      let entities = undefined;
      let keywords = undefined;
      try {
        entities = await this.fetchEntities(text);
      } catch(error) {
        console.log("An error occured during fetching the entities for the WikiHow article");
        console.log(error);
      }
      try {
        keywords = await this.fetchKeywords(text);
      } catch(error) {
        console.log("An error occured during fetching the keywords for the WikiHow article");
        console.log(error);
      }
      return this.visualizeEntitiesAndKeywords(text, entities, keywords);
    },
    generateSummary: async function(text, method) {
      try {
        let summary = await this.fetchSummary(text, method);
        let processedSummary = await this.processEntitiesAndKeywords(summary);
        return processedSummary;
      } catch(error) {
        console.log("An error occured during fetching the summary for the WikiHow article with the method " + method);
        console.log(error);
      }
    },
    updateCharts: async function(method) {
      let ratings = await this.fetchRatings(method);
      console.log(ratings);

      this.chartData[method] = {
        labels: ["1 Star", "2 Stars", "3 Stars", "4 Stars", "5 Stars"],
        datasets: [
          {
            label: 'Method ' + method,
            backgroundColor: ["red", "blue", "green", "blue", "red"],
            borderColor: ["black", "grey", "black", "grey", "black"],
            data: ratings,
            borderWidth: 5,
          },
        ]
      }
    },
  },
  created() {
    // Execute methods on create
  },
};

function getData(url = '', data = {}) {
  // Default options are marked with *
  return fetch(url, {
    method: 'GET', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, cors, *same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json',
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrer: 'no-referrer', // no-referrer, *client
  })
    .then(response => response.json()); // parses JSON response into native JavaScript objects
}

function postData(url = '', data = {}) {
  // Default options are marked with *
  return fetch(url, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, cors, *same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json',
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrer: 'no-referrer', // no-referrer, *client
    body: JSON.stringify(data), // body data type must match "Content-Type" header
  })
    .then(response => response.json()); // parses JSON response into native JavaScript objects
}

// Accordion
function myFunction(id) {
  var x = document.getElementById(id);
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
    x.previousElementSibling.className += " w3-theme-d1";
  } else {
    x.className = x.className.replace("w3-show", "");
    x.previousElementSibling.className =
      x.previousElementSibling.className.replace(" w3-theme-d1", "");
  }
}

// Used to toggle the menu on smaller screens when clicking on the menu button
function openNav() {
  var x = document.getElementById("navDemo");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else {
    x.className = x.className.replace(" w3-show", "");
  }
}
</script>

<style>
  @import "https://www.w3schools.com/w3css/4/w3.css";
  @import "https://www.w3schools.com/lib/w3-theme-light-green.css";
  @import "https://fonts.googleapis.com/css?family=Open+Sans'";
  @import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css";
  html, body, h1, h2, h3, h4, h5 {font-family: "Open Sans", sans-serif}
  .wikihow-padding-top {
    padding-top: 16px;
  }
</style>
