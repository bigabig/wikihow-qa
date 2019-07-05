<template>
  <div class="autocomplete" id="myInputParent" style="width:100%;">
    <input autocomplete="off" id="myInput" v-model="$parent.input[$parent.currentPage][$parent.evalMode]" v-on:input="$parent.fetchAutocompletion()" class="w3-border w3-padding" type="text" style="width:100%">
  </div>
</template>

<script>
  export default {
    name: 'Autocomplete',
    props: ['suggestions'],
    mounted() {
      // Execute methods on create
      let input = document.getElementById("myInput");

      /*execute a function when someone writes in the text field:*/
      // input.addEventListener("input", this.onWrite);

      /*execute a function presses a key on the keyboard:*/
      input.addEventListener("keydown", this.onKeydown);

      /*execute a function when someone clicks in the document:*/
      document.addEventListener("click", (e) => {
        this.closeAllLists(e.target);
      });
    },
    data: function () {
      return {
        currentFocus: 0,
      }
    },
    watch: {
      suggestions: function (val) {
        console.log("SOMETING CHANGED :D");
        this.onWrite();
      },
    },
    methods: {
      onKeydown: function(e) {
        console.log("onkeydown");
        console.log(e);
        console.log(e.keyCode);
        let input = document.getElementById("myInput");
        var x = document.getElementById(input.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode === 40) {
          /*If the this.arrow DOWN key is pressed,
          increase the currentFocus variable:*/
          this.currentFocus++;
          /*and and make the current item more visible:*/
          this.addActive(x);
        } else if (e.keyCode === 38) { //up
          /*If the this.arrow UP key is pressed,
          decrease the currentFocus variable:*/
          this.currentFocus--;
          /*and and make the current item more visible:*/
          this.addActive(x);
        } else if (e.keyCode === 13) {
          /*If the ENTER key is pressed, prevent the form from being submitted,*/
          e.preventDefault();
          if (this.currentFocus > -1) {
            /*and simulate a click on the "active" item:*/
            if (x) x[this.currentFocus].click();
          }
        }
      },
      onWrite: function () {
        console.log("onwrite");
        let inputParent = document.getElementById("myInputParent");
        let input = document.getElementById("myInput");
        var a, b, i;
        let val = input.value;
        /*close any already open lists of autocompleted values*/
        this.closeAllLists();
        if (!val) {
          return false;
        }
        this.currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        a = document.createElement("DIV");
        a.setAttribute("id", input.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        /*append the DIV element as a child of the autocomplete container:*/

        inputParent.appendChild(a);
        /*for each item in the array...*/
        console.log(this.suggestions);
        for (i = 0; i < this.suggestions.length; i++) {
          /*check if the item starts with the same letters as the text field value:*/
          if (this.suggestions[i].substr(0, val.length).toUpperCase() === val.toUpperCase()) {
            /*create a DIV element for each matching element:*/
            b = document.createElement("DIV");
            /*make the matching letters bold:*/
            b.innerHTML = "<strong>" + this.suggestions[i].substr(0, val.length) + "</strong>";
            b.innerHTML += this.suggestions[i].substr(val.length);
            /*insert a input field that will hold the current array item's value:*/
            b.innerHTML += "<input type='hidden' value='" + this.suggestions[i] + "'>";
            /*execute a function when someone clicks on the item value (DIV element):*/
            b.addEventListener("click", (e) => {
              /*insert the value for the autocomplete text field:*/
              console.log("on auto complete click :)");
              console.log(e);
              input.value = e.srcElement.getElementsByTagName("input")[0].value;
              this.$parent.input[this.$parent.currentPage][this.$parent.evalMode] = input.value;
              /*close the list of autocompleted values,
              (or any other open lists of autocompleted values:*/
              this.closeAllLists();
            });
            a.appendChild(b);
          }
        }
      },
      addActive: function(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        this.removeActive(x);
        if (this.currentFocus >= x.length) this.currentFocus = 0;
        if (this.currentFocus < 0) this.currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[this.currentFocus].classList.add("autocomplete-active");
      },
      removeActive: function(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
          x[i].classList.remove("autocomplete-active");
        }
      },
      closeAllLists: function(elmnt) {
        let inputParent = document.getElementById("myInputParent");
        let input = document.getElementById("myInput");
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
          if (elmnt !== x[i] && elmnt !== input) {
            inputParent.removeChild(x[i]);
          }
        }
      }
    }
  }
</script>

<style scoped>
  /*the container must be positioned relative:*/
</style>
