package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;
import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}

  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();
    //System.out.println(messages);
    int sum = 0;
    int count =0;
    long k = 0;
    long j = messages.get(0).getCreatedAt();
    // implement me!
    for (int i = 1; i < messages.size(); i++) {
      if(messages.get(i).isTeamMember() && !messages.get(i - 1).isTeamMember()) {
        k = messages.get(i).getCreatedAt();
        k -= j;
        count++;
        sum += k;
      }
      else if (!messages.get(i).isTeamMember() && messages.get(i - 1).isTeamMember()){
        j = messages.get(i).getCreatedAt();
      }
      else {

      }
    }

    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(sum / count)
        .build();
  }
}
