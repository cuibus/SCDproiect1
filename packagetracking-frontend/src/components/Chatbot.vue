<template>
  <v-dialog v-model="showDialog" max-width="400px" class="bg-transparent">
    <div class="dialogClass">
      <v-textarea
          label="chatbot answer"
          v-model="chatResponse"
          readonly
      ></v-textarea>
      <v-text-field v-model="question"
      ></v-text-field>
      <v-btn @click="send">Create</v-btn>
    </div>
  </v-dialog>
</template>

<script>
import axios from 'axios';

export default {
  name: 'chatbot',
  data() {
    return {
      showDialog: Boolean,
      question: "",
      chatResponse: ""
    }
  },
  methods: {
    async send() {
      try {
        const response = await axios.post('http://localhost:8083/chatbot', this.question)
        console.log("question sent to chatbot", response.data);
        this.chatResponse = response.data
      } catch (error) {
        console.error("Error sending to chatbot:", error);
      }
    },
  }
};
</script>

<style scoped>
.dialogClass {
  padding: 20px;
  background-color: white;
}
</style>