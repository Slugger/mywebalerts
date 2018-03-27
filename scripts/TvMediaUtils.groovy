@GrabResolver(name='six43tech', root='https://dl.bintray.com/six43tech/six43tech-oss-mvn')
@Grab('ca.six43tech.tvmedia:tvmedia-client:0.0.1')
import ca.six43tech.tvmedia.models.*
import groovy.xml.MarkupBuilder
import org.apache.velocity.tools.generic.EscapeTool

class TvMediaUtils {
   static private final EscapeTool ESC = new EscapeTool()

   String credits(Movie m) {
      def sb = new StringBuilder()

      def cast = []
      m.cast.findAll { it.role == 'Director' }.each {
         def data = "${ESC.html(it.person.name)}"
         if(it.person.imdbId)
            cast << "<a href=\"http://www.imdb.com/name/$it.person.imdbId\" target=\"_blank\">$data</a>"
         else
            cast << data
      }
      if(cast.size())
         sb << "<div style=\"margin:2px 0px 2px 0px;\"><b>Directed by:</b> ${cast.join(', ')}</div>\n"

      cast = []
      m.cast.findAll { it.role == 'Writer' }.each {
         def data = "${ESC.html(it.person.name)}"
         if(it.person.imdbId)
            cast << "<a href=\"http://www.imdb.com/name/$it.person.imdbId\" target=\"_blank\">$data</a>"
         else
            cast << data
      }
      if(cast.size())
         sb << "<div style=\"margin: 2px 0px 2px 0px;\"><b>Written by:</b> ${cast.join(', ')}</div>\n"

      cast = []
      m.cast.findAll { it.role == 'Cast' }.each {
         def data = "${ESC.html(it.person.name)}"
         if(it.person.imdbId)
            cast << "<a href=\"http://www.imdb.com/name/$it.person.imdbId\" target=\"_blank\">$data</a>"
         else
            cast << data
      }
      if(cast.size())
         sb << "<div style=\"margin:2px 0px 2px 0px;\"><b>Starring:</b> ${cast.join(', ')}</div>\n"
      sb.toString()
   }

   String movieQuickFacts(Movie m) {
      def facts = []
      facts << m.releaseDate.format('MMM d')
      facts << m.mpaaRating ?: m.usRating
      facts << m.genre?.name
      facts << (m.starRating > 0 ? "$m.starRating/5" : null)
      facts << (m.trailerUrl ? "<a href=\"$m.trailerUrl\" target=\"_blank\">Trailer</a>" : null)
      facts << (m.imdbId ? "<a href=\"http://www.imdb.com/title/$m.imdbId\" target=\"_blank\">IMDB</a>" : null)
      facts.findAll { it }.join(' | ')
   }

   String airingQuickFacts(Airing a) {
      def facts = []
      facts << a.showType
      facts << a.rating
      facts.findAll { it }.join(' | ')
   }

   String channelDetails(Channel c) {
      def writer = new StringWriter()
      def markup = new MarkupBuilder(writer)
      markup.div {
         if(c.webLink)
            a(href: c.webLink) { genLogoOrCallsign(markup, c) }
         else
            i("Aring on ${genLogoOrCallsign(markup, c)}")
      }
      writer.toString()
   }

   private String genStationName(def s) {
      s.network ?: s.callsign ?: s.name
   }

   private void genLogoOrCallsign(def markup, def c) {
      if(c.logo)
         markup.img(src: "//cdn.tvpassport.com/image/station/76x28/$c.logo", alt: genStationName(c))
      else
         markup.span { genStationName(c) }
   }
}
