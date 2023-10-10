<template>
  <div class="personality-test">
    <h1>Personality Test</h1>
    <div v-if="!testStarted">
      <button @click="startTest" class="next-button">Start</button>
    </div>
    <div v-else-if="currentPage >= 0 && currentPage < questions.length" class="question-container">
      <div class="question-progress">
        Question {{ currentPage + 1 }} of {{ questions.length }}
      </div>
      <p class="question">
        {{ questions[currentPage].question }}
      </p>
      <div class="answer-options" v-for="(answer, key) in questions[currentPage].answers" :key="key">
        <input
          type="radio"
          :id="`q${currentPage}_a${key}`"
          :value="key"
          v-model="selectedAnswers[currentPage]"
          class="radio-input"
        />
        <label :for="`q${currentPage}_a${key}`" class="radio-label">
          <span :class="['radio-letter', { 'selected': selectedAnswers[currentPage] === key }]">{{ key }}</span>{{ answer }}
        </label>
      </div>
      <button @click="moveForward" class="next-button">Next</button>
    </div>
    <div v-else>
      <div v-if="!personalityResult">
        <button @click="calculatePersonality" class="next-button">Calculate</button>
      </div>
      <div v-else>
        <button @click="retakeTest" class="next-button">Retake</button>
      </div>
    </div>
    <div v-if="personalityResult" class="result">
      {{ personalityResult }}
    </div>
    <div v-if="validationError" class="error-message">
      Please answer the current question.
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      questions: [],
      selectedAnswers: [],
      personalityResult: null,
      validationError: false,
      currentPage: 0,
      testStarted: false
    };
  },

  created() {
    this.fetchQuestions();
  },

  methods: {
    startTest() {
      this.testStarted = true;
    },

     retakeTest() {
      this.startTest = true
      this.currentPage = 0;
      this.selectedAnswers = [];
      this.personalityResult = null;
      this.validationError = false;
    },

    moveForward() {
      if (this.selectedAnswers[this.currentPage] === undefined) {
        this.validationError = true;
        return;
      }

      this.validationError = false;
      this.currentPage++;

      if (this.currentPage >= this.questions.length) {
        this.currentPage = this.questions.length;
      }
    },

    calculatePersonality() {
      if (this.selectedAnswers.includes(undefined)) {
        this.validationError = true;
        return;
      }

      axios
        .post("http://localhost:8080/api/calculate", this.selectedAnswers)
        .then((response) => {
          this.personalityResult = response.data;
          this.validationError = false;
        })
        .catch((error) => {
          console.error(error);
        });

        this.startTest = false;
    },

    fetchQuestions() {
      axios
        .get("http://localhost:8080/api/questions")
        .then((response) => {
          const shuffledQuestions = response.data.sort(
            () => Math.random() - 0.5
          );

          this.questions = shuffledQuestions.slice(0, 5);
        })
        .catch((error) => {
          console.error("Error fetching questions:", error);
        });
    },
  },
};
</script>

<style>
.personality-test {
  max-width: 700px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  background-color: #f0f0f0;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.question-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.question {
  font-size: 18px;
  margin-bottom: 10px;
}

.answer-options {
  margin-top: 10px;
}

.radio-input {
  display: none; /* Hide the default radio button */
}

.radio-label {
  display: flex;
  align-items: center;
  margin-left: 10px;
  cursor: pointer;
}

.radio-letter {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #007BFF;
  color: #fff;
  text-align: center;
  font-weight: bold;
  margin-right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px; /* Make all letters the same size */
}

.radio-letter.selected {
  background-color: #032b57; /* Change color for selected letters */
}

.next-button, .calculate-button {
  background-color: #96c7fa;
  color: #fff;
  border: none;
  padding: 10px 20px;
  margin-top: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.next-button:hover, .calculate-button:hover {
  background-color: #0056b3;
}

.result {
  font-size: 18px;
  margin-top: 20px;
  color: #333;
  font-weight: bold;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>

