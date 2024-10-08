package xiaome.commands;

import xiaome.TaskList;
import xiaome.XiaoMeException;
import xiaome.Storage;
import xiaome.task.Task;

/**
 * Represents a command that deletes a task from the task list.
 * The task to be deleted is identified by its index in the list.
 */
public class DeleteCommand extends Command {

    String userInput;

    /**
     * Constructs a DeleteCommand with the user input specifying the task to be deleted.
     *
     * @param userInput the user input in the format 'delete <index>'
     */
    public DeleteCommand(String userInput) {
        this.userInput = userInput;
        this.isExit = false;
    }

    /**
     * Executes the command to delete a task. The task to be deleted is identified by its index.
     *
     * The expected format for user input is 'delete <index>'.
     *
     * @param tasks the list of tasks from which the task will be deleted
     * @return a success message with details of the removed task
     * @throws XiaoMeException if the user input format is invalid or the index is out of bounds
     */
    @Override
    public String execute(TaskList tasks) throws XiaoMeException {
        try {
            String[] markWords = userInput.split(" ");
            int index = Integer.parseInt(markWords[1]) - 1;
            Task temp = tasks.getTask(index);

            tasks.deleteTask(index);

            Storage.saveFile(tasks.getTasks());

            return "\tNoted. I've removed this task:\n"
                           + "\t\t" + temp + "\n"
                           + "\tNow you have " + tasks.getSize() + " tasks in the list.";

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new XiaoMeException("""
                    \tHEY delete should be followed by a valid integer""");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new XiaoMeException("""
                    \tInteger provided does not have a corresponding task""");
        }
    }
}
