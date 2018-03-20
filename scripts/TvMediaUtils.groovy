@GrabResolver(name='six43tech', root='https://dl.bintray.com/six43tech/six43tech-oss-mvn')
@Grab('ca.six43tech.tvmedia:tvmedia-client:0.0.1')
import ca.six43tech.tvmedia.models.Channel
import groovy.xml.MarkupBuilder

class TvMediaUtils {
   static String channelDetails(Channel c) {
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

   static private String genStationName(def s) {
      s.network ?: s.callsign ?: s.name
   }

   static private void genLogoOrCallsign(def markup, def c) {
      if(c.logo)
         markup.img(src: "//cdn.tvpassport.com/image/station/76x28/$c.logo", alt: genStationName(c))
      else
         markup.span { genStationName(c) }
   }
}
