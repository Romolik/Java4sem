package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

import java.util.Random;

/**
 * The command changes the direction of movement. The pointer moves in a random
 * direction.
 */
public class MoveRandom implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves in a
	 * random direction.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("MoveRandom command is used");
		Random random = new Random ();
		if (random.nextBoolean ()) {
			int randomDirection = random.nextBoolean () ? 1 : -1;
			context.getPointer ().setMove (randomDirection, 0);
		} else {
			int randomDirection = random.nextBoolean () ? 1 : -1;
			context.getPointer ().setMove (0, randomDirection);
		}
	}

}
