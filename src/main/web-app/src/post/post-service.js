angular.module('documentflowApp').factory('PostService', function () {
    let posts = [];

    return {
        getAll: function () {
            return posts;
        },
        delete: function (post) {
            posts = posts.filter(p => p.id !== post.id);
        },
        setAll: function (postList) {
            posts = postList;
        },
        add: function (post) {
            if (this.getById(post.id) == null) {
                posts.push(post);
            }
        },
        getById: function (id) {
            return posts.find(post => post.id === id);
        }
    };
});