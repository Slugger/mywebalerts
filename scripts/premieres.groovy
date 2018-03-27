@GrabResolver(name='six43tech', root='https://dl.bintray.com/six43tech/six43tech-oss-mvn')
@Grab('ca.six43tech.tvmedia:tvmedia-client:0.0.1')
import ca.six43tech.tvmedia.TvMediaClient
import ca.six43tech.tvmedia.http.options.*
import TvMediaUtils 

def tvmClient = new TvMediaClient(props.apiKey)
def start = Date.parse('yyyy-MM-dd HH:mm', "${new Date().format('yyyy-MM-dd')} 18:00")
log.debug "Checking for premieres starting at $start"
def listings = tvmClient.getLineup('6340').getListingsQuickly(new StartDateOption(start), new EndDateOption(new Date(start.time + 24 * 3600000)))
def premieres = listings.findAll {
    it.showName != 'Movie' && (it.seriesPremiere || it.seasonPremiere)
}.unique { it.showId }
def chanInfo = [:]
premieres.each {
   chanInfo[it.channel.id] = new TvMediaUtils().channelDetails(it.channel)
}
[desc: 'Today\'s Premieres', airings: premieres, tmpl: 'airings.tmpl', size: premieres?.size(), subject: 'Today\'s TV Premieres', chanInfo: chanInfo, utils: new TvMediaUtils()]
