<template>
  <v-app>
    <v-main>
      <packages-view/>

      <v-btn style="align-content: center" @click="openCreatePackageDialog">
        Create new package
      </v-btn>

      <add-package
          :currentUser="username"
          ref="addPostDialog"
      ></add-package>

      <v-btn style="align-content: center; margin-left: 10px" @click="openChatbot">
        Chatbot
      </v-btn>

      <chatbot
          ref="chatbotDialog"
      ></chatbot>
    </v-main>
  </v-app>
</template>

<script>
import PackagesView from './components/PackagesView.vue'
import AddPackage from "@/components/AddPackage.vue";
import Chatbot from "@/components/Chatbot.vue";

export default {
  name: 'App',

  components: {
    PackagesView,
    AddPackage,
    Chatbot
  },

  data: () => ({
    username: "Nume utilizator"
  }),
  mounted() {
    this.$refs.addPostDialog.showDialog = false
    this.$refs.chatbotDialog.showDialog = false
  },
  methods: {
    openCreatePackageDialog() {
      this.$refs.addPostDialog.showDialog = true
    },
    openChatbot() {
      this.$refs.chatbotDialog.showDialog = true
    }
  }
}
</script>
