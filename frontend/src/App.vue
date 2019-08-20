<template>
  <div id="app">

    <div id="error-popup" :class="{'wikihow-no-display': errorMessage === 'string'}" class="wikihow-popup-anim wikihow-popup w3-panel w3-red w3-card-4 w3-content">
      <h3>Error :(</h3>
      <p>{{errorMessage}}</p>
    </div>

    <div id="success-popup" :class="{'wikihow-no-display': successMessage === 'string'}" class="wikihow-popup-anim wikihow-popup w3-panel w3-green w3-card-4 w3-content">
      <h3>Success :)</h3>
      <p>{{successMessage}}</p>
    </div>

    <div class="w3-theme-l5">
      <!-- Navbar -->
      <div class="w3-top">
        <div class="w3-bar w3-theme w3-left-align w3-large">
          <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
          <span class="w3-bar-item w3-padding-large w3-theme-d5"><i class="fa fa-file-text w3-margin-right"></i>The LT Project</span>
          <a v-on:click="currentPage = 0" href="#" v-bind:class="{ 'w3-theme-d3': currentPage === 0 }" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-cogs"></i> Main</a>
          <a v-on:click="currentPage = 1" href="#" v-bind:class="{ 'w3-theme-d3': currentPage === 1 }" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-users"></i> User Study</a>
          <a v-on:click="currentPage = 2" href="#" v-bind:class="{ 'w3-theme-d3': currentPage === 2 }" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-bar-chart"></i> Evaluation</a>
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

            <!-- Search Container -->
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
                    <p v-if="evalMode === 0"><autocomplete :suggestions="autocomplete"></autocomplete></p>
<!--                    <input v-on:keydown="fetchAutocompletion()" v-model="input[currentPage][evalMode]" class="w3-border w3-padding" type="text" style="width:100%">-->
                    <p v-if="evalMode === 1"><textarea v-model="input[currentPage][evalMode]" class="w3-border w3-padding" rows="5" style="width:100%"></textarea></p>
                    <p>
                      <label class="w3-opacity">Method:</label><br>
                      <select v-model="method[currentPage][evalMode]" class="w3-select w3-border w3-padding w3-white" name="option">
                        <option v-if="evalMode === 0" value="gold">Cheating</option>
                        <option value="textrank">TextRank</option>
                        <option value="network">Pointer-Generator Network</option>
                        <option value="bertsum">BertSum</option>
                      </select>
                    </p>
                    <button v-if="evalMode === 0" v-on:click="answerQuestion" type="button" class="w3-button w3-theme"><i class="fa fa-search"></i>  Search!</button>
                    <button v-if="evalMode === 1" v-on:click="answerText" type="button" class="w3-button w3-theme"><i class="fa fa-search"></i>  Summarize!</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- END Search Container -->

            <!-- Article Answer Container -->
            <template v-if="evalMode === 0">
              <div v-if="summaries[currentPage][evalMode][currentMethod[currentPage][evalMode]][0] !== 'string'" class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity">generated by {{ currentMethod[currentPage][evalMode] }}</span>
                <h4>Short Instructions</h4>
                <hr class="w3-clear">
                <ul>
                  <li v-for="sentence in summaries[currentPage][evalMode][currentMethod[currentPage][evalMode]]">
                    {{ sentence }}
                  </li>
                </ul>
                <button v-on:click="showBetterSummary = !showBetterSummary" type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i> Summary not good? Provide a better one!</button>
                <div v-if="showBetterSummary" class="w3-container w3-card w3-theme-l4 w3-margin-bottom" style="margin-top:-16px">
                  <h4>Write a better summary</h4>
                  <p><textarea v-model="betterSummary" :readonly="!summaryAllowed[currentMethod[currentPage][evalMode]]" v-bind:class="{ 'w3-opacity': !summaryAllowed[currentMethod[currentPage][evalMode]] }" class="w3-border w3-padding" rows="5" style="width:100%"></textarea></p>
                  <p v-bind:class="{ 'wikihow-danger': betterSummaryLength > maxLength }">{{betterSummaryLength}} / {{maxLength}}</p>
                  <button v-on:click="submitSummary" :disabled="betterSummaryLength > maxLength || !summaryAllowed[currentMethod[currentPage][evalMode]]" type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-check"></i> Submit</button>
                </div>
              </div>

              <div v-if="text[currentPage][evalMode] !== 'string'" class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity">a WikiHow Article</span>
                <h4>Detailed Instructions</h4>
                <hr class="w3-clear">
                <p>{{text[currentPage][evalMode]}}</p>
              </div>
            </template>
            <!-- END Article Answer Container -->

            <!-- Text Answer Container -->
            <template v-if="evalMode === 1">
              <div v-if="summaries[currentPage][evalMode][currentMethod[currentPage][evalMode]][0] !== 'string'" class="w3-container w3-card w3-white w3-round w3-margin"><br>
                <span class="w3-right w3-opacity">generated by {{ currentMethod[currentPage][evalMode] }}</span>
                <h4>Summary</h4>
                <hr class="w3-clear">
                <ul>
                  <li v-for="sentence in summaries[currentPage][evalMode][currentMethod[currentPage][evalMode]]">
                    {{ sentence }}
                  </li>
                </ul>
              </div>
            </template>
            <!-- Text Answer Container -->

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
                    <p v-if="evalMode === 0"><autocomplete :suggestions="autocomplete"></autocomplete></p>
                    <p v-if="evalMode === 1"><textarea v-model="input[currentPage][evalMode]" id="textarea-eval" class="w3-border w3-padding" rows="5" style="width:100%"></textarea></p>
                    <p>
                      <label class="w3-opacity">Method:</label><br>
                      <select v-model="method[currentPage][evalMode]" class="w3-select w3-border w3-padding w3-white" name="option">
                        <option value="all" selected>All Methods</option>
                        <option v-if="evalMode === 0" value="gold">Cheating</option>
                        <option value="textrank">TextRank</option>
                        <option value="network">Pointer-Generator Network</option>
                        <option value="bertsum">BertSum</option>
                      </select>
                    </p>
                    <p>
                      <label class="w3-opacity">Named Entities:</label><br>
                      <template v-for="entity in allEntities">
                        <input v-on:change="updateHighlights" type="checkbox" :id="entity" :value="entity" v-model="checkedEntities"><label :for="entity"><span :class="entity" class="NER-NO-REMOVE NER">{{entity}}</span></label>
                      </template>
                    </p>
                    <p>
                        <label class="w3-opacity">Keywords:</label><br>
                        <input v-on:change="updateKeywords" type="radio" id="keywordoption1" name="keywordoption" value="none" v-model="keywordOption"><label style="margin-left:4px;" for="keywordoption1">Don't show keywords</label><br>
                        <input v-on:change="updateKeywords" type="radio" id="keywordoption2" name="keywordoption" value="text" v-model="keywordOption"><label style="margin-left:4px;" for="keywordoption2">Hightlight keywords in text</label><br>
                        <input v-on:change="updateKeywords" type="radio" id="keywordoption3" name="keywordoption" value="list" v-model="keywordOption"><label style="margin-left:4px;" for="keywordoption3">Show keywords as list</label>
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

            <div v-if="currentMethod[currentPage][evalMode] === 'all'" v-for="summarizationMethod in allMethods" class="w3-row-padding wikihow-padding-top">
              <div v-if="!(summarizationMethod === 'gold' && evalMode === 1) && summaries[currentPage][evalMode][summarizationMethod][0] !== 'string'" class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-container w3-padding">
                    <h6 class="w3-opacity">Summary {{summarizationMethod}}</h6>
                    <ul>
                      <li v-for="sentence in summaries[currentPage][evalMode][summarizationMethod]" v-html="sentence"></li>
                    </ul>
                    <hr class="w3-clear">
                    <p style="font-size:18px; margin-bottom: 8px;">
                      <span class="w3-opacity">Submit a rating: </span>
                      <span v-on:click="rateMethod(currentPage, evalMode, summarizationMethod, 1)" @mouseover="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?1:hover[currentPage][evalMode][summarizationMethod]" @mouseleave="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?0:hover[currentPage][evalMode][summarizationMethod]" :class="{ checked: hover[currentPage][evalMode][summarizationMethod] >= 1 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, summarizationMethod, 2)" @mouseover="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?2:hover[currentPage][evalMode][summarizationMethod]" @mouseleave="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?0:hover[currentPage][evalMode][summarizationMethod]" :class="{ checked: hover[currentPage][evalMode][summarizationMethod] >= 2 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, summarizationMethod, 3)" @mouseover="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?3:hover[currentPage][evalMode][summarizationMethod]" @mouseleave="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?0:hover[currentPage][evalMode][summarizationMethod]" :class="{ checked: hover[currentPage][evalMode][summarizationMethod] >= 3 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, summarizationMethod, 4)" @mouseover="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?4:hover[currentPage][evalMode][summarizationMethod]" @mouseleave="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?0:hover[currentPage][evalMode][summarizationMethod]" :class="{ checked: hover[currentPage][evalMode][summarizationMethod] >= 4 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, summarizationMethod, 5)" @mouseover="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?5:hover[currentPage][evalMode][summarizationMethod]" @mouseleave="hover[currentPage][evalMode][summarizationMethod]=ratingAllowed[currentPage][evalMode][summarizationMethod]?0:hover[currentPage][evalMode][summarizationMethod]" :class="{ checked: hover[currentPage][evalMode][summarizationMethod] >= 5 }" class="fa fa-star"></span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div v-if="currentMethod[currentPage][evalMode] !== 'all' && summaries[currentPage][evalMode][currentMethod[currentPage][evalMode]][0] !== 'string'" class="w3-row-padding wikihow-padding-top">
              <div class="w3-col m12">
                <div class="w3-card w3-round w3-white">
                  <div class="w3-container w3-padding">
                    <h6 class="w3-opacity">Summary {{currentMethod[currentPage][evalMode]}}</h6>
                    <ul>
                      <li v-for="sentence in summaries[currentPage][evalMode][currentMethod[currentPage][evalMode]]" v-html="sentence"></li>
                    </ul>
                    <template v-if="showKeywordList">
                      <hr class="w3-clear">
                      <span class="w3-opacity">Keywords:</span>
                      <ul style="margin-top:0;">
                        <li v-for="keyword in foundKeywords[currentPage][evalMode][currentMethod[currentPage][evalMode]]">{{keyword.word}} - {{keyword.score}}</li>
                      </ul>
                    </template>
                    <hr class="w3-clear">
                    <p style="font-size:18px; margin-bottom: 8px;">
                      <span class="w3-opacity">Submit a rating:</span>
                      <span v-on:click="rateMethod(currentPage, evalMode, currentMethod[currentPage][evalMode], 1)" @mouseover="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?1:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" @mouseleave="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?0:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" :class="{ checked: hover[currentPage][evalMode][currentMethod[currentPage][evalMode]] >= 1 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, currentMethod[currentPage][evalMode], 2)" @mouseover="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?2:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" @mouseleave="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?0:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" :class="{ checked: hover[currentPage][evalMode][currentMethod[currentPage][evalMode]] >= 2 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, currentMethod[currentPage][evalMode], 3)" @mouseover="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?3:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" @mouseleave="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?0:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" :class="{ checked: hover[currentPage][evalMode][currentMethod[currentPage][evalMode]] >= 3 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, currentMethod[currentPage][evalMode], 4)" @mouseover="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?4:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" @mouseleave="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?0:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" :class="{ checked: hover[currentPage][evalMode][currentMethod[currentPage][evalMode]] >= 4 }" class="fa fa-star"></span>
                      <span v-on:click="rateMethod(currentPage, evalMode, currentMethod[currentPage][evalMode], 5)" @mouseover="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?5:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" @mouseleave="hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]=ratingAllowed[currentPage][evalMode][currentMethod[currentPage][evalMode]]?0:hover[currentPage][evalMode][currentMethod[currentPage][evalMode]]" :class="{ checked: hover[currentPage][evalMode][currentMethod[currentPage][evalMode]] >= 5 }" class="fa fa-star"></span>
                    </p>
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!-- Right Column -->
          <div class="w3-col m6">
            <div v-if="text[currentPage][evalMode] !== 'string'" class="w3-container w3-card w3-white w3-round w3-margin"><br>
              <span v-if="evalMode === 0" class="w3-right w3-opacity">a WikiHow Article</span>
              <span v-if="evalMode === 1" class="w3-right w3-opacity">a User Input Text</span>
              <h4 v-if="evalMode === 0">Original Article</h4>
              <h4 v-if="evalMode === 1">Original User Text</h4>
              <hr class="w3-clear">
              <p v-html="text[currentPage][evalMode]"></p>
              <template v-if="showKeywordList">
                <hr class="w3-clear">
                <span class="w3-opacity">Keywords:</span>
                <ul style="margin-top:0;">
                  <li v-for="keyword in foundKeywords[currentPage][evalMode]['text']">{{keyword.word}} - {{keyword.score}}</li>
                </ul>
              </template>
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
                    <div class="w3-row">
                      <div class="w3-col w3-padding m5">
                        <bar-chart :chart-data="barChartData[summarizationMethod]" :options="barChartOptions"></bar-chart>
                      </div>
                      <div class="w3-col w3-padding m5">
                        <line-chart :chart-data="lineChartData[summarizationMethod]" :options="lineChartOptions"></line-chart>
                      </div>
                      <div class="w3-col w3-padding m2">
                        <h6 class="w3-opacity">Statistics</h6>
                        <p># Votes: {{votes[summarizationMethod]}}</p>
                        <p>Avg. Rating: {{avgRating[summarizationMethod]}}</p>
                      </div>
                    </div>
                    <button @click="updateCharts(summarizationMethod)">Refresh</button>
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
import BarChart from './components/BarChart';
import LineChart from './components/LineChart';
import Autocomplete from './components/Autocomplete';

require('@/assets/css/main.css');

export default {
  name: 'app',
  components: {
    BarChart,
    LineChart,
    Autocomplete,
  },
  data: function () {
    return {
      wikihowArticle: {
        "article": "string",
        "filename": "string",
        "full_article": "string",
        "score": 0,
        "suggest_title": "string",
        "summary": "string",
        "title": "string"
      },
      input: [[
        "how to ...",
        "string"
      ],[
        "how to ...",
        "string"
      ]],
      method: [[
        "textrank",
        "textrank"
      ],[
        "textrank",
        "textrank"
      ]],
      currentMethod: [[
        "textrank",
        "textrank"
      ],[
        "textrank",
        "textrank"
      ]],
      allMethods: ["textrank", "gold"],
      currentPage: 0,       // the actual body to show
      evalMode: 0,          // analyze question or own text?
      text: [[
        "string",
        "string"
      ],[
        "string",
        "string"
      ]],
      summaries: [[{
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
      }, {
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
      }],[{
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
      }, {
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
      ]}]],
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
      keywordOption: "none",
      hover: [[{
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      },{
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      }],[{
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      },{
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      }]],
      ratingAllowed: [[{
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      },{
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      }],[{
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      },{
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      }]],
      foundKeywords: [[{
        "text": "string",
        "gold": "string",
        "textrank": "string",
        "network": "string",
        "bertsum": "string"
      },{
        "text": "string",
        "gold": "string",
        "textrank": "string",
        "network": "string",
        "bertsum": "string"
      }],[{
        "text": "string",
        "gold": "string",
        "textrank": "string",
        "network": "string",
        "bertsum": "string",
      },{
        "text": "string",
        "gold": "string",
        "textrank": "string",
        "network": "string",
        "bertsum": "string",
      }]],
      showKeywordList: false,
      votes: {
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      },
      avgRating: {
        "gold": 0,
        "textrank": 0,
        "network": 0,
        "bertsum": 0,
      },
      barChartData: {
        "gold": null,
        "textrank": null,
        "network": null,
        "bertsum": null,
      },
      lineChartData: {
        "gold": null,
        "textrank": null,
        "network": null,
        "bertsum": null,
      },
      barChartOptions: {
        responsive: true,
        scales: {
          xAxes: [{
            ticks: {
              beginAtZero: true,
              stepSize: 1,
            },
          }]
        }
      },
      lineChartOptions: {
        responsive: true,
        scales: {
          xAxes: [{
            type: 'time',
            distribution: 'linear',
            time: {
              unit: 'day'
            }
          }],
          yAxes: [{
            ticks: {
              beginAtZero: true,
              stepSize: 1,
            },
          }]
        },
        // elements: {
        //   line: {
        //     tension: 0 // disables bezier curves
        //   }
        // }
      },
      summaryAllowed: {
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      },
      betterSummary: "",
      showBetterSummary: false,
      maxLength: 512,
      successMessage: "string",
      errorMessage: "string",
      autocomplete: [
        "string"
      ]
    }
  },
  computed: {
    betterSummaryLength: function () {
      return this.betterSummary.length
    }
  },
  watch: {
    currentPage: function (val) {
      // when page changes, update highlighted entities!
      setTimeout(() => {this.updateHighlights();}, 1);

      // when navigating to evaluation, update charts
      if(val === 2) {
        for(let method of this.allMethods) {
          this.updateCharts(method);
        }
      }
    },
    evalMode: function (val) {
      // when eval mode changes, update highlighted entities!
      setTimeout(() => {this.updateHighlights();}, 1);
    }
  },
  methods: {
    showSuccessMessage: function(message) {
      this.successMessage = message;

      let successPopup = document.getElementById('success-popup');
      successPopup.classList.remove('wikihow-popup-anim');
      setTimeout(() => {
        successPopup.classList.add('wikihow-popup-anim');
      }, 5);
    },
    showErrorMessage: async function(message) {
      this.errorMessage = message;

      let errorPopup = document.getElementById('error-popup');
      errorPopup.classList.remove('wikihow-popup-anim');
      setTimeout(() => {
        errorPopup.classList.add('wikihow-popup-anim');
      }, 5);
    },
    resetVars: function(page, mode, methods) {
      for(let method of methods) {
        this.summaries[page][mode][method] = ["string"];
        this.ratingAllowed[page][mode][method] = true;
        this.foundKeywords[page][mode][method] = "string";
        this.hover[page][mode][method] = 0;
      }
      this.text[page][mode] = "string";
      this.foundKeywords[page][mode]["text"] = "string";

      this.summaries = this.summaries.slice(0);
      this.text = this.text.slice(0);
      this.ratingAllowed = this.ratingAllowed.slice(0);
      this.hover = this.hover.slice(0);

      // user provided summary stuff
      this.showBetterSummary = false;
      this.summaryAllowed = {
        "gold": true,
        "textrank": true,
        "network": true,
        "bertsum": true,
      };
      this.betterSummary = "";
    },
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
    updateKeywords: function() {
      if(this.keywordOption === "none") {
        // Dont show keywords in text
        Array.from(document.getElementsByClassName("KEYWORD")).forEach(element => {
          if(element.classList.contains("DISPLAYKEYWORD")) {
            element.classList.remove("DISPLAYKEYWORD");
          }
        });
        this.showKeywordList = false;
      } else if (this.keywordOption === "text") {
        // Show keywords in text
        Array.from(document.getElementsByClassName("KEYWORD")).forEach(element => {
          if(!element.classList.contains("DISPLAYKEYWORD")) {
            element.classList.add("DISPLAYKEYWORD");
          }
        });
        this.showKeywordList = false;
      } else if (this.keywordOption === "list") {
        // Dont show keywords in text
        Array.from(document.getElementsByClassName("KEYWORD")).forEach(element => {
          if(element.classList.contains("DISPLAYKEYWORD")) {
            element.classList.remove("DISPLAYKEYWORD");
          }
        });
        this.showKeywordList = true;
      }
    },
    fetchAutocompletion: async function() {
      let input = this.input[this.currentPage][this.evalMode];
      if(input.length < 5)
        return;
      console.log("Fetching autocompletion " + input);
      let data = await getData('http://localhost:8080/wikihowqa/suggest?text='+input+'&count='+5);
      console.log(data);
      this.autocomplete = data;
    },
    fetchWikihowArticle: async function(question) {
      // get article from wikidata
      console.log("Fetching new WikiHow Artice");
      let data = await postData('http://localhost:8080/wikihowqa/articles', {
        "count": 1,
        "topic": question
      });

      console.log("Success fetching new WikiHow Article");
      if(data.articles !== null && data.articles.length > 0) {
        this.wikihowArticle = data.articles[0];
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
        return data.keywords;
      } else {
        throw new Error("Keywords are empty!");
      }
    },
    fetchRatings: async function(method) {
      console.log("Fetching Ratings for method" + method);
      let data = await getData('http://localhost:8080/wikihowqa/ratings?method='+method);
      return data;
    },
    fetchRatingsOverTime: async function(method) {
      console.log("Fetching Ratings over time for method" + method);
      let data = await getData('http://localhost:8080/wikihowqa/ratingsOverTime?method='+method);
      return data;
    },
    rateMethod: async function(page, mode, method, rating) {
      if(!this.ratingAllowed[page][mode][method])
        return;

      console.log("Rating Method " + method + " with " + rating + " stars");
      this.ratingAllowed[page][mode][method] = false;
      let message = await postData('http://localhost:8080/wikihowqa/rate', {
        "method": method,
        "rating": rating,
        "title": this.wikihowArticle.title
      });
      console.log(message);
      this.showSuccessMessage("Thank you for this rating.");
    },
    submitSummary: async function() {
      let currentMethod = this.currentMethod[0][0];

      if(this.betterSummary.length < 5) {
        this.showErrorMessage("Your summary is way to short!");
        return;
      }

      if(!this.summaryAllowed[currentMethod])
        return;

      if(this.betterSummaryLength > this.maxLength)
        return;

      console.log("Submitting Summary for Article " + this.wikihowArticle.title + " and Method " + currentMethod);
      this.summaryAllowed[currentMethod] = false;
      let message = await postData('http://localhost:8080/wikihowqa/sum', {
        "method": currentMethod,
        "summary": this.betterSummary,
        "title": this.wikihowArticle.title
      });
      console.log(message);
      this.showBetterSummary = false;
      this.showSuccessMessage("Thanks for contributing a new summary! Very appreciated.");
    },
    answerQuestion: async function() {
      this.currentMethod[0][0] = this.method[0][0];
      let currentMethod = this.currentMethod[0][0];
      this.currentMethod = this.currentMethod.slice(0);

      console.log("answer question " + this.input[0][0]);

      this.resetVars(0, 0, [currentMethod]);

      // first fetch the wikihow article
      let article = undefined;
      try {
        article = await this.fetchWikihowArticle(this.input[0][0]);
        this.wikihowArticle = article;
        this.text[0][0] = article.full_article;
        this.text = this.text.slice(0);
      } catch(error) {
        console.log("An error occured during fetching the WikiHow article");
        console.log(error);
        return;
      }

      if(currentMethod !== 'gold') {
        // summarize article depending on the selected method
        try {
          let summary;
          if(currentMethod === 'network') {
            console.log("Using short article for summarization with network");
            summary = await this.fetchSummary(article.article, currentMethod);
          } else {
            console.log("Using long article for summarization");
            summary = await this.fetchSummary(article.full_article, currentMethod);
          }
          this.summaries[0][0][currentMethod] = summary.split(".").filter(sentence => sentence.length > 0);
        } catch(error) {
          console.log("An error occured during fetching the summary for the WikiHow article with the method " + currentMethod);
          console.log(error);
        }
      } else {
        this.summaries[0][0][currentMethod] = article.summary.split(".").filter(sentence => sentence.length > 0);
      }
      this.summaries = this.summaries.slice(0);
    },
    answerText: async function() {
      this.currentMethod[0][1] = this.method[0][1];
      let currentMethod = this.currentMethod[0][1];
      this.currentMethod = this.currentMethod.slice(0);

      this.resetVars(0, 1, [currentMethod]);

      // get input from textarea
      this.text[0][1] = this.input[0][1];
      let input = this.input[0][1];
      this.text = this.text.slice(0);


      if(input !== undefined && input !== null && input.length > 5) {
        // summarize article depending on the selected method
        try {
          let summary = await this.fetchSummary(input, currentMethod);
          this.summaries[0][1][currentMethod] = summary.split(".").filter(sentence => sentence.length > 0);
        } catch(error) {
          console.log("An error occured during fetching the summary for the WikiHow article with the method " + currentMethod);
          console.log(error);
        }
      }
      this.summaries = this.summaries.slice(0);
    },
    evaluateQuestion: async function() {
      this.currentMethod[1][0] = this.method[1][0];
      let currentMethod = this.currentMethod[1][0];
      this.currentMethod = this.currentMethod.slice(0);

      if(currentMethod === 'all')
        this.resetVars(1, 0, this.allMethods);
      else
        this.resetVars(1, 0, [currentMethod]);

      // first fetch the wikihow article
      let article = undefined;
      try {
        article = await this.fetchWikihowArticle(this.input[1][0]);
        this.text[1][0] = article.full_article;
        this.text = this.text.slice(0);
      } catch(error) {
        console.log("An error occured during fetching the WikiHow article");
        console.log(error);
        return;
      }

      console.log("Processing WikiHow article");

      // Get Named Entities, Get Keywords, Then visualize them
      this.text[1][0] = await this.processEntitiesAndKeywords(this.text[1][0], "text");
      this.text = this.text.slice(0);

      // summarize article
      // depending on selection, use all methods
      if(currentMethod === 'all') {
        for(let method of this.allMethods) {
          if(method === 'gold') {
            let summary = article.summary;
            let processedSummary = await this.processEntitiesAndKeywords(summary, method);
            this.summaries[1][0][method] = processedSummary.split(".").filter(sentence => sentence.length > 0);
          } else {
            // Get Summary
            let summary;
            if(currentMethod === 'network') {
              console.log("Using short article for summarization with network");
              summary = await this.generateSummary(article.article, method);
            } else {
              console.log("Using long article for summarization with " + method);
              summary = await this.generateSummary(article.full_article, method);
            }
            this.summaries[1][0][method] = summary.split(".").filter(sentence => sentence.length > 0);
          }
        }
      // the gold method
      } else if (currentMethod === 'gold') {
        let summary = article.summary;
        let processedSummary = await this.processEntitiesAndKeywords(summary, currentMethod);
        this.summaries[1][0][currentMethod] = processedSummary.split(".").filter(sentence => sentence.length > 0);
      // or just the selected method
      } else {
        // Get Summary
        let summary;
        if(currentMethod === 'network') {
          console.log("Using short article for summarization with network");
          summary = await this.generateSummary(article.article, currentMethod);
        } else {
          console.log("Using long article for summarization");
          summary = await this.generateSummary(article.full_article, currentMethod);
        }
        this.summaries[1][0][currentMethod] = summary.split(".").filter(sentence => sentence.length > 0);
      }
      this.summaries = this.summaries.slice(0);
    },
    evaluateText: async function() {
      this.currentMethod[1][1] = this.method[1][1];
      let currentMethod = this.currentMethod[1][1];
      this.currentMethod = this.currentMethod.slice(0);

      if(currentMethod === 'all')
        this.resetVars(1, 1, this.allMethods);
      else
        this.resetVars(1, 1, [currentMethod]);

      // get input from textarea
      this.text[1][1] = this.input[1][1];
      let input = this.input[1][1];
      this.text = this.text.slice(0);

      // then process it
      if(input !== undefined && input !== null && input.length > 5) {
        console.log("Processing text");

        // Get Named Entities, Get Keywords, Then visualize them
        this.text[1][1] = await this.processEntitiesAndKeywords(input, "text");
        this.text = this.text.slice(0);

        // summarize input
        // depending on selection, use all methods
        if(currentMethod === 'all') {
          for(let method of this.allMethods) {
            if(method !== 'gold') {
              let summary = await this.generateSummary(input, method);
              this.summaries[1][1][method] = summary.split(".").filter(sentence => sentence.length > 0);
            }
          }
          // or just the selected method
        } else {
          let summary = await this.generateSummary(input, currentMethod);
          this.summaries[1][1][currentMethod] = summary.split(".").filter(sentence => sentence.length > 0);
        }
      }
      this.summaries = this.summaries.slice(0);
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

          let re = new RegExp("((?:^|\\W))"+element.word+"(?:$|\\W)","ig");
          while ((match = re.exec(text)) != null) {
            let spanStart;
            if (this.keywordOption === "text") {
              spanStart = "<span class='DISPLAYKEYWORD KEYWORD'>";
            } else {
              spanStart = "<span class='KEYWORD'>";
            }
            let spanEnd = "</span>";

            replacements.push([match.index, spanStart]);
            replacements.push([match.index + match[1].length + element.word.length, spanEnd]);
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
    processEntitiesAndKeywords: async function(text, forText) {
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
        this.foundKeywords[this.currentPage][this.evalMode][forText] = keywords;
        this.foundKeywords = this.foundKeywords.slice(0);
      } catch(error) {
        console.log("An error occured during fetching the keywords for the WikiHow article");
        console.log(error);
      }
      return this.visualizeEntitiesAndKeywords(text, entities, keywords);
    },
    generateSummary: async function(text, method) {
      try {
        let summary = await this.fetchSummary(text, method);
        let processedSummary = await this.processEntitiesAndKeywords(summary, method);
        return processedSummary;
      } catch(error) {
        console.log("An error occured during fetching the summary for the WikiHow article with the method " + method);
        console.log(error);
      }
    },
    updateCharts: async function(method) {
      let ratings = await this.fetchRatings(method);
      console.log(ratings);

      this.barChartData[method] = {
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
      };

      this.votes[method] = ratings.reduce((a, b) => a + b);

      let totalRating = 0;
      for(let i = 0; i < ratings.length; i++) {
        totalRating += (i+1) * ratings[i];
      }
      this.avgRating[method] = totalRating / this.votes[method];
      this.avgRating[method] = this.avgRating[method].toFixed(2);

      let ratingsOverTime = await this.fetchRatingsOverTime(method);
      console.log(ratingsOverTime);
      this.lineChartData[method] = {
        labels: ratingsOverTime.labels,
        datasets: [{
          label: 'Demo',
          data: ratingsOverTime.data,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255,99,132,1)',
          borderWidth: 1
        }]
      };
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
</style>
