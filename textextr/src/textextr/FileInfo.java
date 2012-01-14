package textextr;

public class FileInfo {
	private int id;
	private String url;
	private String fileName;
	private String folder;
	private boolean downloaded;
	private boolean parsed;
	
	public FileInfo(int id, String url, String fileName, String folder, boolean downloaded, boolean parsed) {
		this.id = id;
		this.url = url;
		this.fileName = fileName;
		this.folder = folder;
		this.downloaded = downloaded;
		this.parsed = parsed;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public boolean isDownloaded() {
		return downloaded;
	}
	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}
	public boolean isParsed() {
		return parsed;
	}
	public void setParsed(boolean parsed) {
		this.parsed = parsed;
	}
}
