package dk.grinhouse.events;

import org.springframework.context.ApplicationEvent;

public class GrinhouseEvent extends ApplicationEvent
{
     private Object argument;
     private EventType type;

     public GrinhouseEvent(Object source, Object argument, EventType type)
     {
          super(source);
          this.argument = argument;
          this.type = type;
     }

     public EventType getType()
     {
          return type;
     }

     public Object getArgument()
     {
          return argument;
     }
}
