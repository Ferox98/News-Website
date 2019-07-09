from flask import Flask, render_template
import webscraper

app = Flask(__name__)

@app.route("/")
def dynamic_page():
    #run scraper
    newsItems = webscraper.fetch_news()
    return render_template('index.html', news = newsItems)
