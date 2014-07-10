module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    less: {
        development: {
            options: {
                paths: ["war/less"]
            },
            files: [{"war/css/main.css": "war/less/main.less"},{"war/css/users.css": "war/less/users.less"}]
        },
        production: {
            options: {
                paths: ["war/less"],
                cleancss: true
            },
            files: [{"war/css/main.css": "war/less/main.less"},{"war/css/users.css": "war/less/users.less"}]
        }
    },
    watch: {
      grunt: { files: ['Gruntfile.js'] },

      less: {
        files: 'war/less/*.less',
        tasks: ['less']
      },
    }
  });

  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('default', ['less','watch']);
}