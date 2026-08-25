/**
 * Provides the core classes and interfaces for command handling in PowerNukkitX.
 * <p>
 * This package contains abstractions and implementations for command registration, execution, permission management,
 * command senders (players, console, entities, NPCs), command mapping, aliases, and annotation-based command systems.
 * <p>
 * Key components include:
 * <ul>
 *   <li>{@link chromatix.command.Command} - Abstract base for all commands.</li>
 *   <li>{@link chromatix.command.CommandSender} - Interface for entities capable of sending commands.</li>
 *   <li>{@link chromatix.command.CommandMap} - Registry and execution system for commands.</li>
 *   <li>{@link chromatix.command.ConsoleCommandSender} - Command sender for the server console.</li>
 *   <li>{@link chromatix.command.ExecutorCommandSender} - Command sender for entities at specific locations.</li>
 *   <li>{@link chromatix.command.NPCCommandSender} - Command sender for NPCs.</li>
 *   <li>{@link chromatix.command.FormattedCommandAlias} - Command alias with argument substitution.</li>
 *   <li>{@link chromatix.command.CommandExecutor} - Interface for command execution listeners.</li>
 * </ul>
 * <p>
 * The package supports advanced features such as multi-command aliases, argument formatting, permission attachments,
 * and integration with plugin systems. It is designed for extensibility and robust command management in Minecraft server environments.
 *
 * @author PowerNukkitX Project Team
 * @since PowerNukkitX 2.0.0
 */
package chromatix.command;