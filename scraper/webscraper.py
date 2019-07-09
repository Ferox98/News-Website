from selenium import webdriver
from bs4 import BeautifulSoup

url = 'http://www.bbc.com/news'

class NewsItem() :
    def __init__(self, img_url, headline, content):
        self.img_url = img_url
        self.headline = headline
        self.content = content

    def add_detail(detail):
        self.detail = detail

newsItems = []

def initialize_driver():
    driver = webdriver.Chrome('chromedriver.exe')
    driver.implicitly_wait(30)
    driver.get(url)
    return driver

def fetch_headline(div_1):
    headline_tag = div_1.find('a', class_=['gs-c-promo-heading gs-o-faux-block-link__overlay-link gel-paragon-bold nw-o-link-split__anchor', 'gs-c-promo-heading gs-o-faux-block-link__overlay-link gel-pica-bold nw-o-link-split__anchor'])
    return headline_tag.text

def fetch_url(div_1):
    # select div with either class attribute
    div_4 = div_1.find('div', class_=['gs-o-responsive-image gs-o-responsive-image--16by9 gs-o-responsive-image--lead', 'gs-o-responsive-image gs-o-responsive-image--16by9'])
    img_tag = div_4.find('img', recursive = False)
    url = img_tag['src']
    if url[0:5] != "https":
        return "N/A"
    return url

def fetch_content(div_1):
    p_tag = div_1.find('p', class_=['gs-c-promo-summary gel-long-primer gs-u-mt nw-c-promo-summary'])
    return p_tag.text

def fetch_news(driver):
    soup_level1 = BeautifulSoup(driver.page_source, 'html.parser')
    # select parent div for all primary and secondary news
    parents = soup_level1.findAll('div', class_=['gel-layout__item nw-c-top-stories__primary-item gel-2/3@l gel-9/16@xxl nw-o-no-keyline nw-o-keyline@s nw-o-no-keyline@m', 'gel-layout__item nw-c-top-stories__secondary-item gel-1/1 gel-1/3@m gel-1/4@l nw-o-keyline nw-o-no-keyline@m gs-u-float-left nw-c-top-stories__secondary-item--1 gel-3/16@xxl gs-u-float-none@xxl gs-u-mt gs-u-mt0@xs', 'gel-layout__item nw-c-top-stories__secondary-item gel-1/1 gel-1/3@m gel-1/4@l nw-o-keyline nw-o-no-keyline@m gs-u-float-left nw-c-top-stories__secondary-item--2 gel-1/5@xxl', 'gel-layout__item nw-c-top-stories__secondary-item gel-1/ 1 gel-1/3@m gel-1/4@l nw-o-keyline nw-o-no-keyline@m gs-u-float-left nw-c-top-stories__secondary-item--3 gel-1/5@xxl', 'gel-layout__item nw-c-top-stories__secondary-item gel-1/1 gel-1/3@m gel-1/4@l nw-o-keyline nw-o-no-keyline@m gs-u-float-left nw-c-top-stories__secondary-item--4 gel-1/5@xxl', 'gel-layout__item nw-c-top-stories__secondary-item gel-1/1 gel-1/3@m gel-1/4@l nw-o-keyline nw-o-no-keyline@m gs-u-float-left nw-c-top-stories__secondary-item--5 gel-1/5@xxl'])
    for div_1 in parents :
        #fetch image, headline and content for each news
        img_url = fetch_url(div_1)
        headline = fetch_headline(div_1)
        content = fetch_content(div_1)
        # create News type and add to list
        news = News()
        news.__init__(img_url, headline, content)
        newsItems.append(news)

def main():
    driver = initialize_driver()
    fetch_news(driver)

main()
