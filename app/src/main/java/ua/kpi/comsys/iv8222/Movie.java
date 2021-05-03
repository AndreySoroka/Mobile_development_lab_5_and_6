package ua.kpi.comsys.iv8222;

public class Movie {
    private final String Title;
    private final String Year;
    private final String imdbID;
    private final String Type;
    private final int Poster;

    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String imdbRating;
    private String imdbVotes;
    private String Production;

    public Movie(String Title, String Year, String imdbID, String Type, int Poster) {
        this.Title = Title;
        this.Year = Year;
        this.imdbID = imdbID;
        this.Type = Type;
        this.Poster = Poster;
    }

    public void setInfo(String Rated, String Released, String Runtime, String Genre, String Director, String Writer, String Actors, String Plot, String Language, String Country, String Awards, String imdbRating, String imdbVotes, String Production){
        this.Rated = Rated;
        this.Released = Released;
        this.Runtime = Runtime;
        this.Genre = Genre;
        this.Director = Director;
        this.Writer = Writer;
        this.Actors = Actors;
        this.Plot = Plot;
        this.Language = Language;
        this.Country = Country;
        this.Awards = Awards;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.Production = Production;
    }

    public String getTitle() {
        return Title;
    }
    public String getYear() {
        return Year;
    }
    public String getimdbID() {
        return imdbID;
    }
    public String getType() {
        return Type;
    }
    public int getPosterID(){
        return Poster;
    }
    public String getRated() { return Rated; }
    public String getReleased() {return Released;}
    public String getRuntime() {return Runtime;}
    public String getGenre() {return Genre;}
    public String getDirector() {return Director;}
    public String getWriter() {return Writer;}
    public String getActors() { return Actors; }
    public String getPlot() {return Plot;}
    public String getLanguage() {return Language;}
    public String getCountry() {return Country;}
    public String getAwards() {return Awards;}
    public String getimdbRating() {return imdbRating;}
    public String getimdbVotes() {return imdbVotes;}
    public String getProduction() {return Production;}


    public String getInfo() {
        return "<font color=#888888>Title: </font> " + Title + "<br> " +
                "<font color=#888888>Year: </font>" + Year + "<br>" +
                "<font color=#888888>Genre: </font>" + Genre + "<br><br>" +
                "<font color=#888888>Director: </font> " + Director + "<br>" +
                "<font color=#888888>Actors: </font> " + Actors + "<br><br> " +
                "<font color=#888888>Country: </font> " + Country + "<br> " +
                "<font color=#888888>Language: </font> " + Language + "<br> " +
                "<font color=#888888>Production: </font> " + Production + "<br>"+
                "<font color=#888888>Released: </font> " + Released + "<br>" +
                "<font color=#888888>Runtime: </font> " + Runtime + "<br><br> " +
                "<font color=#888888>Awards: </font> " + Awards + "<br>" +
                "<font color=#888888>Rating: </font> " + imdbRating + "<br><br> " +
                "<font color=#888888>Plot: </font> " + Plot + "<br>" ;
    }
}

