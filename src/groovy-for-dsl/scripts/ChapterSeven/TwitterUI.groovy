@Grab(group='net.sf.squirrel-sql.thirdparty-non-maven', module='napkinlaf', version='[1.2,)')
import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*
import net.sourceforge.napkinlaf.*

data = []

def results
swing = new SwingBuilder()
swing.lookAndFeel(new NapkinLookAndFeel())
frame = swing.frame(title:'Twitter Search') {
  menuBar {
    menu('File') {
      menuItem 'Exit', actionPerformed: { System.exit(0) }
    }
  }
  panel(layout: new BorderLayout()) {
    panel (constraints:BorderLayout.NORTH) {
      label 'Search for Tweets'
      textField(columns:10, actionPerformed: { event ->
          println "Search for Event ${event.source.text}"
          data = GeeTwitter.search(event.source.text)
          results.model = tableModel(list:data) {
            propertyColumn(header:'Sender',
                           propertyName:'from',preferredWidth:20)
            propertyColumn(header:'Tweet',
                           propertyName:'tweet',preferredWidth:140)
          }
        })
    }
    scrollPane (constraints:BorderLayout.SOUTH){
      results = table() {
        tableModel(list:[]) {
          propertyColumn(header:'Sender',
                         propertyName:'from',preferredWidth:20)
          propertyColumn(header:'Tweet',
                         propertyName:'tweet',preferredWidth:140)
        }
      }
    }   
  }    
}
frame.pack()
frame.show()
