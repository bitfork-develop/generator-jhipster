package <%=packageName%>.web.rest.mapper;

import <%=packageName%>.domain.<%= entityClass %>;
import <%=packageName%>.web.rest.dto.<%= entityClass %>Dto;

import org.mapstruct.*;

/**
 * Mapper for the entity <%= entityClass %> and its DTO <%= entityClass %>Dto.
 */
@Mapper(uses = {<% for (relationshipId in relationships) {
    if (relationships[relationshipId].relationshipType == 'one-to-many' || relationships[relationshipId].relationshipType == 'many-to-many') { %><%= relationships[relationshipId].otherEntityNameCapitalized %>Mapper.class, <% } } %>})
public interface <%= entityClass %>Mapper {
<% for (relationshipId in relationships) {
        if (relationships[relationshipId].relationshipType == 'many-to-one') {
        %>
    @Mapping(source = "<%= relationships[relationshipId].relationshipName %>.id", target = "<%= relationships[relationshipId].otherEntityName %>Id")<% if (relationships[relationshipId].otherEntityFieldCapitalized != '') { %>
    @Mapping(source = "<%= relationships[relationshipId].relationshipName %>.<%=relationships[relationshipId].otherEntityField %>", target = "<%= relationships[relationshipId].otherEntityName %><%= relationships[relationshipId].otherEntityFieldCapitalized %>")<% } } } %>
    <%= entityClass %>Dto <%= entityInstance %>To<%= entityClass %>Dto(<%= entityClass %> <%= entityInstance %>);
<% for (relationshipId in relationships) {
        if (relationships[relationshipId].relationshipType == 'many-to-one') { %>
    @Mapping(target = "<%= relationships[relationshipId].relationshipName %>", ignore = true)<% } else if (relationships[relationshipId].relationshipType == 'many-to-many' && relationships[relationshipId].ownerSide == false) { %>
    @Mapping(target = "<%= relationships[relationshipId].relationshipName %>s", ignore = true)<% } else if (relationships[relationshipId].relationshipType == 'one-to-many') { %>
    @Mapping(target = "<%= relationships[relationshipId].relationshipName %>s", ignore = true)<% } } %>
    <%= entityClass %> <%= entityInstance %>DtoTo<%= entityClass %>(<%= entityClass %>Dto <%= entityInstance %>Dto);
}
