/* Generated By:JavaCC: Do not edit this line. QueryParser.java */
package org.apache.solr.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.QParser;


public class QueryParser extends SolrQueryParserBase implements QueryParserConstants {
  /** The default operator for parsing queries.
   */
  static public enum Operator { OR, AND }

  /** default split on whitespace behavior */
  public static final boolean DEFAULT_SPLIT_ON_WHITESPACE = false;

   public QueryParser(String defaultField, QParser parser) {
    this(new FastCharStream(new StringReader("")));
    init(defaultField, parser);
  }

  /**
   * @see #setSplitOnWhitespace(boolean)
   */
  public boolean getSplitOnWhitespace() {
    return splitOnWhitespace;
  }

  /**
   * Whether query text should be split on whitespace prior to analysis.
   * Default is <code>{@value #DEFAULT_SPLIT_ON_WHITESPACE}</code>.
   */
  public void setSplitOnWhitespace(boolean splitOnWhitespace) {
    this.splitOnWhitespace = splitOnWhitespace;
  }

  private boolean splitOnWhitespace = DEFAULT_SPLIT_ON_WHITESPACE;
  private static Set<Integer> disallowedPostMultiTerm
    = new HashSet<Integer>(Arrays.asList(COLON, STAR, FUZZY_SLOP, CARAT, AND, OR));
  private static boolean allowedPostMultiTerm(int tokenKind) {
    return disallowedPostMultiTerm.contains(tokenKind) == false;
  }

  @Override
  protected Query newFieldQuery(Analyzer analyzer, String field, String queryText,
                                boolean quoted, boolean fieldAutoGenPhraseQueries, boolean fieldEnableGraphQueries)
      throws SyntaxError {
    setAutoGenerateMultiTermSynonymsPhraseQuery(fieldAutoGenPhraseQueries || getAutoGeneratePhraseQueries());
    // Don't auto-quote graph-aware field queries 
    boolean treatAsQuoted = getSplitOnWhitespace()
        ? (quoted || fieldAutoGenPhraseQueries || getAutoGeneratePhraseQueries()) : quoted;
    return super.newFieldQuery(analyzer, field, queryText, treatAsQuoted, false, fieldEnableGraphQueries);
  }

// *   Query  ::= ( Clause )*
// *   Clause ::= ["+", "-"] [<TERM> ":"] ( <TERM> | "(" Query ")" )
  final public int Conjunction() throws ParseException {
  int ret = CONJ_NONE;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
    case OR:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        jj_consume_token(AND);
            ret = CONJ_AND;
        break;
      case OR:
        jj_consume_token(OR);
              ret = CONJ_OR;
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public int Modifiers() throws ParseException {
  int ret = MOD_NONE;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
    case PLUS:
    case MINUS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
              ret = MOD_REQ;
        break;
      case MINUS:
        jj_consume_token(MINUS);
                 ret = MOD_NOT;
        break;
      case NOT:
        jj_consume_token(NOT);
               ret = MOD_NOT;
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

// This makes sure that there is no garbage after the query string
  final public Query TopLevelQuery(String field) throws ParseException, SyntaxError {
  Query q;
    q = Query(field);
    jj_consume_token(0);
    {if (true) return q;}
    throw new Error("Missing return statement in function");
  }

  final public Query Query(String field) throws ParseException, SyntaxError {
  List<BooleanClause> clauses = new ArrayList<BooleanClause>();
  Query q;
  int conj, mods;
    if (jj_2_1(2)) {
      MultiTerm(field, clauses);
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NOT:
      case PLUS:
      case MINUS:
      case BAREOPER:
      case LPAREN:
      case STAR:
      case QUOTED:
      case TERM:
      case PREFIXTERM:
      case WILDTERM:
      case REGEXPTERM:
      case RANGEIN_START:
      case RANGEEX_START:
      case LPARAMS:
      case FILTER:
      case NUMBER:
        mods = Modifiers();
        q = Clause(field);
        addClause(clauses, CONJ_NONE, mods, q);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case OR:
      case NOT:
      case PLUS:
      case MINUS:
      case BAREOPER:
      case LPAREN:
      case STAR:
      case QUOTED:
      case TERM:
      case PREFIXTERM:
      case WILDTERM:
      case REGEXPTERM:
      case RANGEIN_START:
      case RANGEEX_START:
      case LPARAMS:
      case FILTER:
      case NUMBER:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_1;
      }
      if (jj_2_2(2)) {
        MultiTerm(field, clauses);
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
        case OR:
        case NOT:
        case PLUS:
        case MINUS:
        case BAREOPER:
        case LPAREN:
        case STAR:
        case QUOTED:
        case TERM:
        case PREFIXTERM:
        case WILDTERM:
        case REGEXPTERM:
        case RANGEIN_START:
        case RANGEEX_START:
        case LPARAMS:
        case FILTER:
        case NUMBER:
          conj = Conjunction();
          mods = Modifiers();
          q = Clause(field);
        addClause(clauses, conj, mods, q);
          break;
        default:
          jj_la1[6] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    if (clauses.size() == 1 && clauses.get(0).getOccur() == BooleanClause.Occur.SHOULD) {
      Query firstQuery = clauses.get(0).getQuery();
      if ( ! (firstQuery instanceof RawQuery) || ((RawQuery)firstQuery).getTermCount() == 1) {
        {if (true) return rawToNormal(firstQuery);}
      }
    }
    {if (true) return getBooleanQuery(clauses);}
    throw new Error("Missing return statement in function");
  }

  final public Query Clause(String field) throws ParseException, SyntaxError {
  Query q;
  Token fieldToken=null, boost=null;
  Token localParams=null;
  int flags = 0;
    if (jj_2_3(2)) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TERM:
        fieldToken = jj_consume_token(TERM);
        jj_consume_token(COLON);
                                  field = discardEscapeChar(fieldToken.image);
        break;
      case STAR:
        jj_consume_token(STAR);
        jj_consume_token(COLON);
                         field = "*";
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } else {
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BAREOPER:
    case STAR:
    case QUOTED:
    case TERM:
    case PREFIXTERM:
    case WILDTERM:
    case REGEXPTERM:
    case RANGEIN_START:
    case RANGEEX_START:
    case NUMBER:
      q = Term(field);
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      q = Query(field);
      jj_consume_token(RPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
        jj_consume_token(CARAT);
        boost = jj_consume_token(NUMBER);
        break;
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      break;
    case FILTER:
      jj_consume_token(FILTER);
                 flags=startFilter();
      q = Query(field);
      jj_consume_token(RPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
        jj_consume_token(CARAT);
        boost = jj_consume_token(NUMBER);
        break;
      default:
        jj_la1[9] = jj_gen;
        ;
      }
                                                                                             q=getFilter(q); restoreFlags(flags);
      break;
    case LPARAMS:
      localParams = jj_consume_token(LPARAMS);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
        jj_consume_token(CARAT);
        boost = jj_consume_token(NUMBER);
        break;
      default:
        jj_la1[10] = jj_gen;
        ;
      }
                                                           q=getLocalParams(field, localParams.image);
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return handleBoost(q, boost);}
    throw new Error("Missing return statement in function");
  }

  final public Query Term(String field) throws ParseException, SyntaxError {
  Token term, boost=null, fuzzySlop=null, goop1, goop2;
  boolean prefix = false;
  boolean wildcard = false;
  boolean fuzzy = false;
  boolean regexp = false;
  boolean startInc=false;
  boolean endInc=false;
  Query q;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BAREOPER:
    case STAR:
    case TERM:
    case PREFIXTERM:
    case WILDTERM:
    case REGEXPTERM:
    case NUMBER:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TERM:
        term = jj_consume_token(TERM);
        break;
      case STAR:
        term = jj_consume_token(STAR);
                      wildcard=true;
        break;
      case PREFIXTERM:
        term = jj_consume_token(PREFIXTERM);
                            prefix=true;
        break;
      case WILDTERM:
        term = jj_consume_token(WILDTERM);
                          wildcard=true;
        break;
      case REGEXPTERM:
        term = jj_consume_token(REGEXPTERM);
                            regexp=true;
        break;
      case NUMBER:
        term = jj_consume_token(NUMBER);
        break;
      case BAREOPER:
        term = jj_consume_token(BAREOPER);
                          term.image = term.image.substring(0,1);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
      case FUZZY_SLOP:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CARAT:
          jj_consume_token(CARAT);
          boost = jj_consume_token(NUMBER);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case FUZZY_SLOP:
            fuzzySlop = jj_consume_token(FUZZY_SLOP);
                                                        fuzzy=true;
            break;
          default:
            jj_la1[13] = jj_gen;
            ;
          }
          break;
        case FUZZY_SLOP:
          fuzzySlop = jj_consume_token(FUZZY_SLOP);
                                 fuzzy=true;
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case CARAT:
            jj_consume_token(CARAT);
            boost = jj_consume_token(NUMBER);
            break;
          default:
            jj_la1[14] = jj_gen;
            ;
          }
          break;
        default:
          jj_la1[15] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[16] = jj_gen;
        ;
      }
      q = handleBareTokenQuery(getField(field), term, fuzzySlop, prefix, wildcard, fuzzy, regexp);
      break;
    case RANGEIN_START:
    case RANGEEX_START:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RANGEIN_START:
        jj_consume_token(RANGEIN_START);
                        startInc = true;
        break;
      case RANGEEX_START:
        jj_consume_token(RANGEEX_START);
        break;
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RANGE_GOOP:
        goop1 = jj_consume_token(RANGE_GOOP);
        break;
      case RANGE_QUOTED:
        goop1 = jj_consume_token(RANGE_QUOTED);
        break;
      default:
        jj_la1[18] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RANGE_TO:
        jj_consume_token(RANGE_TO);
        break;
      default:
        jj_la1[19] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RANGE_GOOP:
        goop2 = jj_consume_token(RANGE_GOOP);
        break;
      case RANGE_QUOTED:
        goop2 = jj_consume_token(RANGE_QUOTED);
        break;
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RANGEIN_END:
        jj_consume_token(RANGEIN_END);
                      endInc = true;
        break;
      case RANGEEX_END:
        jj_consume_token(RANGEEX_END);
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
        jj_consume_token(CARAT);
        boost = jj_consume_token(NUMBER);
        break;
      default:
        jj_la1[22] = jj_gen;
        ;
      }
      boolean startOpen=false;
      boolean endOpen=false;
      if (goop1.kind == RANGE_QUOTED) {
        goop1.image = goop1.image.substring(1, goop1.image.length()-1);
      } else if ("*".equals(goop1.image)) {
        startOpen=true;
      }
      if (goop2.kind == RANGE_QUOTED) {
        goop2.image = goop2.image.substring(1, goop2.image.length()-1);
      } else if ("*".equals(goop2.image)) {
        endOpen=true;
      }
      q = getRangeQuery(getField(field),
                        startOpen ? null : discardEscapeChar(goop1.image),
                        endOpen ? null : discardEscapeChar(goop2.image), startInc, endInc);
      break;
    case QUOTED:
      term = jj_consume_token(QUOTED);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CARAT:
      case FUZZY_SLOP:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CARAT:
          jj_consume_token(CARAT);
          boost = jj_consume_token(NUMBER);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case FUZZY_SLOP:
            fuzzySlop = jj_consume_token(FUZZY_SLOP);
                                                        fuzzy=true;
            break;
          default:
            jj_la1[23] = jj_gen;
            ;
          }
          break;
        case FUZZY_SLOP:
          fuzzySlop = jj_consume_token(FUZZY_SLOP);
                                 fuzzy=true;
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case CARAT:
            jj_consume_token(CARAT);
            boost = jj_consume_token(NUMBER);
            break;
          default:
            jj_la1[24] = jj_gen;
            ;
          }
          break;
        default:
          jj_la1[25] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[26] = jj_gen;
        ;
      }
      q = handleQuotedTerm(getField(field), term, fuzzySlop);
      break;
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return handleBoost(q, boost);}
    throw new Error("Missing return statement in function");
  }

  final public void MultiTerm(String field, List<BooleanClause> clauses) throws ParseException, SyntaxError {
  Token text;
  List<String> terms = null;
    text = jj_consume_token(TERM);
    if (splitOnWhitespace) {
      Query q = getFieldQuery(getField(field), discardEscapeChar(text.image), false, true);
      addClause(clauses, CONJ_NONE, MOD_NONE, q);
    } else {
      terms = new ArrayList<String>();
      terms.add(discardEscapeChar(text.image));
    }
    if (getToken(1).kind == TERM && allowedPostMultiTerm(getToken(2).kind)) {

    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    label_2:
    while (true) {
      text = jj_consume_token(TERM);
      if (splitOnWhitespace) {
        Query q = getFieldQuery(getField(field), discardEscapeChar(text.image), false, true);
        addClause(clauses, CONJ_NONE, MOD_NONE, q);
      } else {
        terms.add(discardEscapeChar(text.image));
      }
      if (getToken(1).kind == TERM && allowedPostMultiTerm(getToken(2).kind)) {
        ;
      } else {
        break label_2;
      }
    }
    if (splitOnWhitespace == false) {
      Query q = getFieldQuery(getField(field), terms, true);
      addMultiTermClause(clauses, q);
    }
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_3R_7() {
    if (jj_scan_token(TERM)) return true;
    return false;
  }

  private boolean jj_3R_4() {
    if (jj_scan_token(TERM)) return true;
    if (jj_scan_token(COLON)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3R_6() {
    return false;
  }

  private boolean jj_3R_3() {
    if (jj_scan_token(TERM)) return true;
    jj_lookingAhead = true;
    jj_semLA = getToken(1).kind == TERM && allowedPostMultiTerm(getToken(2).kind);
    jj_lookingAhead = false;
    if (!jj_semLA || jj_3R_6()) return true;
    Token xsp;
    if (jj_3R_7()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_7()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3_3() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_4()) {
    jj_scanpos = xsp;
    if (jj_3R_5()) return true;
    }
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_3()) return true;
    return false;
  }

  private boolean jj_3R_5() {
    if (jj_scan_token(STAR)) return true;
    if (jj_scan_token(COLON)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public QueryParserTokenManager token_source;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  /** Whether we are looking ahead. */
  private boolean jj_lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  final private int[] jj_la1 = new int[28];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x6000,0x6000,0x38000,0x38000,0xfb4f8000,0xfb4fe000,0xfb4fe000,0x2400000,0x800000,0x800000,0x800000,0xfb4c0000,0x3a440000,0x4000000,0x800000,0x4800000,0x4800000,0xc0000000,0x0,0x0,0x0,0x0,0x800000,0x4000000,0x800000,0x4800000,0x4800000,0xfb440000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x7,0x7,0x7,0x0,0x0,0x0,0x0,0x7,0x4,0x0,0x0,0x0,0x0,0x0,0xc0,0x8,0xc0,0x30,0x0,0x0,0x0,0x0,0x0,0x4,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[3];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with user supplied CharStream. */
  protected QueryParser(CharStream stream) {
    token_source = new QueryParserTokenManager(stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(CharStream stream) {
    token_source.ReInit(stream);
    token = new Token();
    jj_ntk = -1;
    jj_lookingAhead = false;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  protected QueryParser(QueryParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(QueryParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = jj_lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[40];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 28; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 40; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 3; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
