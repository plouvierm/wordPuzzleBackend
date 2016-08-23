
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.json.JSONArray;

@Path("/puzzleservice")
public class PuzzleService {
	private WordPuzzle wordPuzzle;
	
	private String createJSONForWordPuzzle(String puzzleLetters, int gridwidth) {
		JSONObject jsonObject = new JSONObject();
		ArrayList<Letter> letters = wordPuzzle.getLetters();
		if (letters == null) {
			jsonObject.put("error", "Creation error");
		} else {
			JSONArray wordLetters = new JSONArray();
			for (int i = 0; i < letters.size(); i++) {
				JSONObject letterObject = new JSONObject();
				letterObject.put("letter", "" + letters.get(i).getLetterValue());
				letterObject.put("positionRow", letters.get(i).getPositionRow());
				letterObject.put("positionColumn", letters.get(i).getPositionColumn());
				wordLetters.put(letterObject);
			}
			jsonObject.put("letters", wordLetters);
			jsonObject.put("gridwidth", gridwidth);
			jsonObject.put("createdLetters", puzzleLetters);
		}
		return jsonObject.toString();
	}
	
	@Path("create-puzzle/{letters}/{gridwidth}")
	@GET
	@Produces("application/json")
	public Response createPuzzle(@PathParam("letters") String newLetters, @PathParam("gridwidth") int gridwidth) {
		wordPuzzle = new WordPuzzle(newLetters, gridwidth);
		
		
		String result = createJSONForWordPuzzle(newLetters, gridwidth);
		return Response.status(200)
				.entity(result)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}
	
	@Path("search-word/{letters}/{puzzleWidth}/{word}")
	@GET
	@Produces("application/json")
	public Response searchWord(@PathParam("letters") String puzzleLetters,@PathParam("puzzleWidth") int puzzleWidth, @PathParam("word") String word) {
		wordPuzzle = new WordPuzzle(puzzleLetters, puzzleWidth);
		JSONObject jsonObject = new JSONObject();
		ArrayList<Letter> foundWord = null;
		if (word != null) {
			foundWord = this.wordPuzzle.searchWord(word);
			jsonObject.put("word", word);
		}
		if (foundWord == null) {
			jsonObject.put("error", "Word not found");
		} else {
			JSONArray wordLetters = new JSONArray();
			for (int i = 0; i < foundWord.size(); i++) {
				JSONObject letterObject = new JSONObject();
				letterObject.put("letter", "" + foundWord.get(i).getLetterValue());
				letterObject.put("positionRow", foundWord.get(i).getPositionRow());
				letterObject.put("positionColumn", foundWord.get(i).getPositionColumn());
				wordLetters.put(letterObject);
			}
			jsonObject.put("letters", wordLetters);
		}
		ArrayList<Letter> letters = this.wordPuzzle.getLetters();
		JSONArray wordLetters = new JSONArray();
		for (int i = 0; i < letters.size(); i++) {
			JSONObject letterObject = new JSONObject();
			letterObject.put("letter", "" + letters.get(i).getLetterValue());
			letterObject.put("positionRow", letters.get(i).getPositionRow());
			letterObject.put("positionColumn", letters.get(i).getPositionColumn());
			wordLetters.put(letterObject);
		}
		jsonObject.put("createdletters", wordLetters);
		String result = jsonObject.toString();
		return Response.status(200)
				.entity(result)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}
	
}
