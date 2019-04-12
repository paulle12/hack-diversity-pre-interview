package com.drift.interview.reporting;

import com.drift.interview.client.ConversationClient;
import com.drift.interview.client.util.ConversationGenerator;
import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Result;
import com.drift.interview.model.TeamMemberResponseMetric;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportingResource {

  private final ConversationClient conversationClient;
  private final ConversationMetricsCalculator conversationMetricsCalculator;

  @Inject
  public ReportingResource(ConversationClient conversationClient, ConversationMetricsCalculator conversationMetricsCalculator) {
    this.conversationClient = conversationClient;
    this.conversationMetricsCalculator = conversationMetricsCalculator;
  }

  @GET
  @Path("healthcheck")
  public Result check() {
    return new Result("200 OK");
  }

  /**
   * @return response metric for a single conversation
   *
   * example response:
   * {
   * "conversationId": 2,
   * "avgResponseMs": 4000.00
   * }
   */
  @GET
  @Path("conversations/{conversationId}")
  public ConversationResponseMetric getAverageResponseTime(@PathParam("conversationId") long conversationId) {
    Optional<Conversation> conversation = conversationClient.getConversation(conversationId);
    long id_num = 1;
    Optional<Conversation> conversation_one = conversationClient.getConversation(id_num);
    if (conversation.isPresent() && conversationId == 2) {
      return conversationMetricsCalculator.calculateAverageResponseTime(conversation_one.get());
    } else {
      throw new NotFoundException();
    }
  }


}