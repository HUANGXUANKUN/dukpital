package duke;


import duke.command.Command;
import duke.core.*;

/**
 * Represents <code>duke.Duke</code>, a Personal Assistant to help
 * users tracking their progress.
 */
public class Duke {
    /**
     * A <code>duke.core.Storage</code> object that handles reading tasks from a local
     * file and saving them to the same file.
     */
    private Storage storage;
    /**
     * A <code>duke.core.TaskList</code> object that deals with add, delete, mark as done,
     * find functions of a list of tasks.
     */
    private TaskList tasks;
    /**
     * A <code>duke.core.Ui</code> object that deals with interactions with the user.
     */
    private  Ui ui;
    /**
     * Constructs a <code>duke.Duke</code> object with a relative file path.
     * Initialize the user interface and reads tasks from the specific text file.
     * @param filePath A string that represents the path of the local file
     *          used for storing tasks.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeExceptionThrow e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }
    /**
     * Runs the <code>duke.Duke</code> program.
     * Reads user input until a "bye" message is received.
     */
    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.Parse(fullCommand);
                c.run(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeExceptionThrow e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
    /**
     * Starts the <code>duke.Duke</code> program by passing in a specific file
     * path.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }
}