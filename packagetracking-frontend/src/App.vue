<template>
  <v-app>
    <v-main>

      <v-btn color="secondary" class="mb-4" @click="showChatbot = true">
        Chatbot
      </v-btn>

      <Chatbot v-model="showChatbot" />

      <v-btn color="primary" class="mb-4" @click="showAddDialog = true">
        AddPackage
      </v-btn>

      <AddPackages
        v-model="showAddDialog"
        :current-user="'TestUser'"
        @package-created="refreshPackages"
      />

      <PackagesView ref="packagesView" />
    </v-main>
  </v-app>
</template>

<script>
import AddPackages from './components/AddPackages.vue'
import PackagesView from './components/PackagesView.vue'
import Chatbot from './components/Chatbot.vue'

export default {
  name: 'App',

  components: {
    AddPackages,
    PackagesView,
    Chatbot
  },

  data() {
    return {
      showAddDialog: false,
      showChatbot: false
    };
  },

  methods: {
    refreshPackages() {
      this.$refs.packagesView.fetchPackages();
    }
  }
};
</script>


