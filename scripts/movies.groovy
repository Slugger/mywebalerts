@GrabResolver(name='six43tech', root='https://dl.bintray.com/six43tech/six43tech-oss-mvn')
@Grab('ca.six43tech.tvmedia:tvmedia-client:0.0.1')
import ca.six43tech.tvmedia.TvMediaClient
import ca.six43tech.tvmedia.http.options.*

def tvmClient = new TvMediaClient(props.apiKey)
def start = Date.parse('yyyy-MM-dd HH:mm', "${new Date().format('yyyy-MM-dd')} 0:00")
def end = start + 7
log.debug "Checking for new movies being released between $start and $end"
def listings = tvmClient.searchMovies(new ReleasedAfterOption(start), new ReleasedBeforeOption(end))
log.debug "Found ${listings.size()} new releases!"
[desc: 'A list of new movies scheduled for theatrical release in the US over the next week.', movies: listings, tmpl: 'movies.tmpl', size: listings?.size(), subject: 'This Week\'s Theatrical Movie Releases']
