package command;

import java.util.HashMap;
import java.util.Map;

import app.Application;

public class CommandManager {

    public enum Type {
        COPY,
        CUT,
        PASTE,
        NEWEDITOR,
        UNDO
    }

    private Application app;
    private Map<String, Command> commands;

    public CommandManager(Application app) {
        this.app = app;
    };

    /**
     * Call this method to get a default command or to edit a macro created with {@link #createMacro}
     * @param app the application you are working on
     * @param name one of {@link Type} or a macro identifier
     * @return a {@link Command} object
     */
    public Command get(Type name) {
        if(commands == null) {
            commands = new HashMap<>();
            commands.put(Type.COPY.toString(), new CopyCommand(app));
            commands.put(Type.CUT.toString(), new CutCommand(app));
            commands.put(Type.PASTE.toString(), new PasteCommand(app));
            commands.put(Type.NEWEDITOR.toString(), new NewEditorCommand(app));
            commands.put(Type.UNDO.toString(), new UndoCommand(app));
        }
        // pass the reference, no need to clone commands
        return commands.get(name.toString());
    }

    // overload to get created macros
    public Command get(String name) {
        return commands.get(name.toString());
    }

    public Command createMacro(String name) {
        commands.put(name, new MacroCommand(app, name));
        return commands.get(name);
    }

}