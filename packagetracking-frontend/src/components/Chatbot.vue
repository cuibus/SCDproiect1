<template>
  <v-dialog v-model="internalDialog" max-width="400px" class="bg-transparent">
    <div class="dialogClass">
      <v-textarea
          label="chatbot answer"
          v-model="chatResponse"
          readonly
      ></v-textarea>

      <v-text-field v-model="question" label="Ask something..."></v-text-field>

      <v-btn color="primary" @click="send">Ask</v-btn>
    </div>
  </v-dialog>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Chatbot',

  props: {
    modelValue: {   // <- pentru v-model
      type: Boolean,
      required: true
    }
  },

  data() {
    return {
      internalDialog: this.modelValue,
      question: "",
      chatResponse: ""
    }
  },

  watch: {
    modelValue(newVal) {
      this.internalDialog = newVal;
    },
    internalDialog(newVal) {
      this.$emit("update:modelValue", newVal);
    }
  },

  methods: {
    async send() {
      try {
        const response = await axios.post(
          'http://localhost:8082/chatbot',
             { question: this.question },      
             { headers: { 'Content-Type': 'application/json' } }
        );

        this.chatResponse = response.data;
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


