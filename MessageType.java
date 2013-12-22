
public enum MessageType{
    PING,
    ACK,
    ACK_INDEX,
    LEAVING_NETWORK,
    SEARCH,
    SEARCH_RESPONSE,
    INDEX,
    ROUTING_INFO,
    JOINING_NETWORK_RELAY_SIMPLIFIED,
    JOINING_NETWORK_SIMPLIFIED,
   //internal messages
    REFRESH_ROUTING_TABLE
}

/**

{
    "type": "JOINING_NETWORK_SIMPLIFIED", // a string
    "node_id": "42", // a non-negative number of order 2'^32^', indicating the id of the joining node).
    "target_id": "42", // a non-negative number of order 2'^32^', indicating the target node for this message.
    "ip_address": "199.1.5.2" // the ip address of the joining node
}
{
    "type": "JOINING_NETWORK_RELAY_SIMPLIFIED", // a string
    "node_id": "42", // a non-negative number of order 2'^32^', indicating the id of the joining node).
    "target_id": "42", // a non-negative number of order 2'^32^', indicating the target node for this message.
    "gateway_id": "34", // a non-negative number of order 2'^32^', of the gateway node
}

{
    "type": "ROUTING_INFO", // a string
    "gateway_id": "34", // a non-negative number of order 2'^32^', of the gateway node
    "node_id": "42", // a non-negative number of order 2'^32^', indicating the target node (and also the id of the joining node).
    "ip_address": "199.1.5.2" // the ip address of the node sending the routing information
    "route_table":
    [
        {
            "node_id": "3", // a non-negative number of order 2'^32^'.
            "ip_address": "199.1.5.3" // the ip address of node 3
        },
        {
            "node_id": "22", // a non-negative number of order 2'^32^'.
            "ip_address": "199.1.5.4" // the ip address of  node 22
        }
    ]
}

{
    "type": "LEAVING_NETWORK", // a string
    "node_id": "42", // a non-negative number of order 2'^32^' identifying the leaving node.
}

{
    "type": "INDEX", //string
    "target_id": "34", //the target id
    "sender_id": "34", // a non-negative number of order 2'^32^', of the message originator
    "keyword": "XXX", //the word being indexed
    "link": [
               "http://www.newindex.com", // the url the word is found in
               "http://www.xyz.com"
              ]
}

{
    "type": "SEARCH", // string
    "word": "apple", // The word to search for
    "node_id": "34",  // target node id
    "sender_id": "34", // a non-negative number of order 2'^32^', of this message originator
}

{
    "type": "SEARCH_RESPONSE",
    "word": "word", // The word to search for
    "node_id": "45",  // target node id
    "sender_id": "34", // a non-negative number of order 2'^32^', of this message originator
    "response":
    [
        {
            url: "www.dsg.cs.tcd.ie/",  //url
            rank: "32"  //rank
        },
        {
             url: "www.scss.tcd.ie/courses/mscnds/",  //url
             rank: "1" //rank
        }
    ]
}

{
    "type": "PING", // a string
    "target_id": "23", // a non-negative number of order 2'^32^', identifying the suspected dead node.
    "sender_id": "56", // a non-negative number of order 2'^32^', identifying the originator 
                               //    of the ping (does not change)
   "ip_address": "199.1.5.4" // the ip address of  node sending the message (changes each hop)
}

{
    "type": "ACK", // a string
    "node_id": "23", // a non-negative number of order 2'^32^', identifying the suspected dead node.
    "ip_address": "199.1.5.4" // the ip address of  sending node, this changes on each hop (or used to hold the keyword in an ACK message returned following an INDEX message - see note below)
}

{
    "type": "ACK_INDEX", // a string
    "node_id": "23", // a non-negative number of order 2'^32^', identifying the target node.
    "keyword": "fish" // the keyword from the original INDEX message 
}
 */
