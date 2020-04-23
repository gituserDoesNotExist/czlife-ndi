function loadEvents () {

    this.source = null;

    this.start = function () {

        var commentTable = document.getElementById("events");

        this.source = new EventSource("/event/stream");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var event = JSON.parse(event.data);

            var row = commentTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);

            cell0.className = "author-style";
            cell0.innerHTML = event.id;

            cell1.className = "date";
            cell1.innerHTML = event.date;

        });

        this.source.onerror = function () {
            this.close();
        };

    };

    this.stop = function() {
        this.source.close();
    }

}

comment = new loadEvents();

/*
 * Register callbacks for starting and stopping the SSE controller.
 */
window.onload = function() {
    comment.start();
};
window.onbeforeunload = function() {
    comment.stop();
}
