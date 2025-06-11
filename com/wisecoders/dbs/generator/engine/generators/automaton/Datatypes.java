package com.wisecoders.dbs.generator.engine.generators.automaton;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class Datatypes {
  private static final Map a;
  
  private static final Automaton b;
  
  private static final Set c;
  
  private static final Set d;
  
  private static final Set e;
  
  private static final String[] f = new String[] { 
      "BasicLatin", "Latin-1Supplement", "LatinExtended-A", "LatinExtended-B", "IPAExtensions", "SpacingModifierLetters", "CombiningDiacriticalMarks", "Greek", "Cyrillic", "Armenian", 
      "Hebrew", "Arabic", "Syriac", "Thaana", "Devanagari", "Bengali", "Gurmukhi", "Gujarati", "Oriya", "Tamil", 
      "Telugu", "Kannada", "Malayalam", "Sinhala", "Thai", "Lao", "Tibetan", "Myanmar", "Georgian", "HangulJamo", 
      "Ethiopic", "Cherokee", "UnifiedCanadianAboriginalSyllabics", "Ogham", "Runic", "Khmer", "Mongolian", "LatinExtendedAdditional", "GreekExtended", "GeneralPunctuation", 
      "SuperscriptsandSubscripts", "CurrencySymbols", "CombiningMarksforSymbols", "LetterlikeSymbols", "NumberForms", "Arrows", "MathematicalOperators", "MiscellaneousTechnical", "ControlPictures", "OpticalCharacterRecognition", 
      "EnclosedAlphanumerics", "BoxDrawing", "BlockElements", "GeometricShapes", "MiscellaneousSymbols", "Dingbats", "BraillePatterns", "CJKRadicalsSupplement", "KangxiRadicals", "IdeographicDescriptionCharacters", 
      "CJKSymbolsandPunctuation", "Hiragana", "Katakana", "Bopomofo", "HangulCompatibilityJamo", "Kanbun", "BopomofoExtended", "EnclosedCJKLettersandMonths", "CJKCompatibility", "CJKUnifiedIdeographsExtensionA", 
      "CJKUnifiedIdeographs", "YiSyllables", "YiRadicals", "HangulSyllables", "CJKCompatibilityIdeographs", "AlphabeticPresentationForms", "ArabicPresentationForms-A", "CombiningHalfMarks", "CJKCompatibilityForms", "SmallFormVariants", 
      "ArabicPresentationForms-B", "Specials", "HalfwidthandFullwidthForms", "Specials", "OldItalic", "Gothic", "Deseret", "ByzantineMusicalSymbols", "MusicalSymbols", "MathematicalAlphanumericSymbols", 
      "CJKUnifiedIdeographsExtensionB", "CJKCompatibilityIdeographsSupplement", "Tags" };
  
  private static final String[] g = new String[] { 
      "Lu", "Ll", "Lt", "Lm", "Lo", "L", "Mn", "Mc", "Me", "M", 
      "Nd", "Nl", "No", "N", "Pc", "Pd", "Ps", "Pe", "Pi", "Pf", 
      "Po", "P", "Zs", "Zl", "Zp", "Z", "Sm", "Sc", "Sk", "So", 
      "S", "Cc", "Cf", "Co", "Cn", "C" };
  
  private static final String[] h = new String[] { 
      "NCName", "QName", "Char", "NameChar", "URI", "anyname", "noap", "whitespace", "whitespacechar", "string", 
      "boolean", "decimal", "float", "integer", "duration", "dateTime", "time", "date", "gYearMonth", "gYear", 
      "gMonthDay", "gDay", "hexBinary", "base64Binary", "NCName2", "NCNames", "QName2", "Nmtoken2", "Nmtokens", "Name2", 
      "Names", "language" };
  
  static {
    a = new LinkedHashMap<>();
    b = Automaton.f(Automaton.a(" \t\n\r").F());
    c = new TreeSet(Arrays.asList((Object[])f));
    d = new TreeSet(Arrays.asList((Object[])g));
    e = new TreeSet(Arrays.asList((Object[])h));
  }
  
  public static void main(String[] paramArrayOfString) {
    long l = System.currentTimeMillis();
    boolean bool = Automaton.b(true);
    b();
    Automaton.b(bool);
    System.out.println("Storing automata...");
    for (Map.Entry entry : a.entrySet())
      a((String)entry.getKey(), (Automaton)entry.getValue()); 
    System.out.println("Time for building automata: " + System.currentTimeMillis() - l + "ms");
  }
  
  public static Automaton a(String paramString) {
    Automaton automaton = (Automaton)a.get(paramString);
    if (automaton == null) {
      automaton = f(paramString);
      a.put(paramString, automaton);
    } 
    return automaton;
  }
  
  public static boolean b(String paramString) {
    return c.contains(paramString);
  }
  
  public static boolean c(String paramString) {
    return d.contains(paramString);
  }
  
  public static boolean d(String paramString) {
    return e.contains(paramString);
  }
  
  public static boolean e(String paramString) {
    try {
      Datatypes.class.getClassLoader().getResource(paramString + ".aut").openStream().close();
    } catch (IOException iOException) {
      return false;
    } 
    return true;
  }
  
  private static Automaton f(String paramString) {
    try {
      URL uRL = Datatypes.class.getClassLoader().getResource(paramString + ".aut");
      System.out.println("load " + paramString + ".aut");
      return Automaton.a(uRL.openStream());
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return null;
    } catch (ClassNotFoundException classNotFoundException) {
      classNotFoundException.printStackTrace();
      return null;
    } 
  }
  
  private static void a(String paramString, Automaton paramAutomaton) {
    String str = System.getProperty("dk.brics.automaton.datatypes");
    if (str == null)
      str = "build"; 
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(str + "/" + str + ".aut");
      try {
        paramAutomaton.a(fileOutputStream);
        fileOutputStream.close();
      } catch (Throwable throwable) {
        try {
          fileOutputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  private static void b() {
    String[] arrayOfString1 = { 
        "Extender", "[〱-〵ゝ-ゞー-ヾ·ːˑ·ـๆໆ々]", "CombiningChar", "[̀-͠ͅ-҃͡-֑҆-֣֡-ֹֻ-ֽׁ-ׂً-ْۖ-ۜ۝-۟۠-ۤۧ-۪ۨ-ۭँ-ःा-ौ॑-॔ॢ-ॣঁ-ঃী-ৄে-ৈো-্ৢ-ৣੀ-ੂੇ-ੈੋ-੍ੰ-ੱઁ-ઃા-ૅે-ૉો-્ଁ-ଃା-ୃେ-ୈୋ-୍ୖ-ୗஂ-ஃா-ூெ-ைொ-்ఁ-ఃా-ౄె-ైొ-్ౕ-ౖಂ-ಃಾ-ೄೆ-ೈೊ-್ೕ-ೖം-ഃാ-ൃെ-ൈൊ-്ิ-ฺ็-๎ິ-ູົ-ຼ່-ໍ༘-ཱ༙-྄྆-ྋྐ-ྕྙ-ྭྱ-ྷ⃐-〪⃜-़়्ֿٰ〯ׄািৗਂ਼ਾਿ઼଼ௗൗัັ༹༵༷༾༿ྗྐྵ゙゚⃡]", "Digit", "[0-9٠-٩۰-۹०-९০-৯੦-੯૦-૯୦-୯௧-௯౦-౯೦-೯൦-൯๐-๙໐-໙༠-༩]", "Ideographic", "[一-龥〡-〩〇]", "BaseChar", "[A-Za-zÀ-ÖØ-öø-ÿĀ-ıĴ-ľŁ-ňŊ-žƀ-ǃǍ-ǰǴ-ǵǺ-ȗɐ-ʨʻ-ˁΈ-ΊΎ-ΡΣ-ώϐ-ϖϢ-ϳЁ-ЌЎ-яё-ќў-ҁҐ-ӄӇ-ӈӋ-ӌӐ-ӫӮ-ӵӸ-ӹԱ-Ֆա-ֆא-תװ-ײء-غف-يٱ-ڷں-ھۀ-ێې-ۓۥ-ۦअ-हक़-ॡঅ-ঌএ-ঐও-নপ-রশ-হড়-ঢ়য়-ৡৰ-ৱਅ-ਊਏ-ਐਓ-ਨਪ-ਰਲ-ਲ਼ਵ-ਸ਼ਸ-ਹਖ਼-ੜੲ-ੴઅ-ઋએ-ઑઓ-નપ-રલ-ળવ-હଅ-ଌଏ-ଐଓ-ନପ-ରଲ-ଳଶ-ହଡ଼-ଢ଼ୟ-ୡஅ-ஊஎ-ஐஒ-கங-சஞ-டண-தந-பம-வஷ-ஹఅ-ఌఎ-ఐఒ-నప-ళవ-హౠ-ౡಅ-ಌಎ-ಐಒ-ನಪ-ಳವ-ಹೠ-ೡഅ-ഌഎ-ഐഒ-നപ-ഹൠ-ൡก-ฮา-ำเ-ๅກ-ຂງ-ຈດ-ທນ-ຟມ-ຣສ-ຫອ-ຮາ-ຳເ-ໄཀ-ཇཉ-ཀྵႠ-Ⴥა-ჶᄂ-ᄃᄅ-ᄇᄋ-ᄌᄎ-ᄒᅔ-ᅕᅟ-ᅡᅭ-ᅮᅲ-ᅳᆮ-ᆯᆷ-ᆸᆼ-ᇂḀ-ẛẠ-ỹἀ-ἕἘ-Ἕἠ-ὅὈ-Ὅὐ-ὗὟ-ώᾀ-ᾴᾶ-ᾼῂ-ῄῆ-ῌῐ-ΐῖ-Ίῠ-Ῥῲ-ῴῶ-ῼK-Åↀ-ↂぁ-ゔァ-ヺㄅ-ㄬ가-힣ΆΌϚϜϞϠՙەऽলਫ਼ઍઽૠଽஜೞะຄຊຍລວະຽᄀᄉᄼᄾᅀᅌᅎᅐᅙᅣᅥᅧᅩᅵᆞᆨᆫᆺᇫᇰᇹὙὛὝιΩ℮]", 
        "Letter", "<BaseChar>|<Ideographic>", "NCNameChar", "<Letter>|<Digit>|[-._]|<CombiningChar>|<Extender>", "NameChar", "<NCNameChar>|:", "Nmtoken", "<NameChar>+", "NCName", "(<Letter>|_)<NCNameChar>*", 
        "Name", "(<Letter>|[_:])<NameChar>*", "QName", "(<NCName>:)?<NCName>", "Char", "[\t\n\r -퟿-�]|[?-?][?-?]", "whitespacechar", "[ \t\n\r]" };
    System.out.println("Building XML automata...");
    Map<?, ?> map1 = a(arrayOfString1);
    a("NCName", map1);
    a("QName", map1);
    a("Char", map1);
    a("NameChar", map1);
    a("Letter", map1);
    a("whitespacechar", map1);
    a(a, "whitespace", b);
    String[] arrayOfString2 = { 
        "digit", "[0-9]", "upalpha", "[A-Z]", "lowalpha", "[a-z]", "alpha", "<lowalpha>|<upalpha>", "alphanum", "<alpha>|<digit>", 
        "hex", "<digit>|[a-f]|[A-F]", "escaped", "%<hex><hex>", "mark", "[-_.!~*'()]", "unreserved", "<alphanum>|<mark>", "reserved", "[;/?:@&=+$,\\[\\]]", 
        "uric", "<reserved>|<unreserved>|<escaped>", "fragment", "<uric>*", "query", "<uric>*", "pchar", "<unreserved>|<escaped>|[:@&=+$,]", "param", "<pchar>*", 
        "segment", "<pchar>*(;<param>)*", "path_segments", "<segment>(/<segment>)*", "abs_path", "/<path_segments>", "uric_no_slash", "<unreserved>|<escaped>|[;?:@&=+$,]", "opaque_part", "<uric_no_slash><uric>*", 
        "port", "<digit>*", "IPv4address", "(<digit>{1,3}\\.){3}<digit>{1,3}", "hexseq", "<hex>{1,4}(:<hex>{1,4})*", "hexpart", "<hexseq>|<hexseq>::<hexseq>?|::<hexseq>", "IPv6address", "<hexpart>(:<IPv4address>)?", 
        "toplabel", "<alpha>|(<alpha>(<alphanum>|-)*<alphanum>)", "domainlabel", "<alphanum>|(<alphanum>(<alphanum>|-)*<alphanum>)", "hostname", "(<domainlabel>\\.)*<toplabel>\\.?", "host", "<hostname>|<IPv4address>|\\[<IPv6address>\\]", "hostport", "<host>(:<port>)?", 
        "userinfo", "(<unreserved>|<escaped>|[;:&=+$,])*", "server", "((<userinfo>\\@)?<hostport>)?", "reg_name", "(<unreserved>|<escaped>|[$,;:@&=+])+", "authority", "<server>|<reg_name>", "scheme", "<alpha>(<alpha>|<digit>|[-+.])*", 
        "rel_segment", "(<unreserved>|<escaped>|[;@&=+$,])+", "rel_path", "<rel_segment><abs_path>?", "net_path", "//<authority><abs_path>?", "hier_part", "(<net_path>|<abs_path>)(\\?<query>)?", "relativeURI", "(<net_path>|<abs_path>|<rel_path>)(\\?<query>)?", 
        "absoluteURI", "<scheme>:(<hier_part>|<opaque_part>)", "URI", "(<absoluteURI>|<relativeURI>)?(\\#<fragment>)?" };
    System.out.println("Building URI automaton...");
    a("URI", a(arrayOfString2));
    a(a, "anyname", Automaton.f(Automaton.a('{').a(((Automaton)a.get("URI")).y()).a(Automaton.a('}')).E().a(((Automaton)a.get("NCName")).y())));
    a(a, "noap", (new RegExp("~(@[@%]@)")).a());
    String[] arrayOfString3 = { 
        "_", "[ \t\n\r]*", "d", "[0-9]", "Z", "[-+](<00-13>:<00-59>|14:00)|Z", "Y", "(<d>{4,})&~(0000)", "M", "<01-12>", 
        "D", "<01-31>", "T", "<00-23>:<00-59>:<00-59>|24:00:00", "B64", "[A-Za-z0-9+/]", "B16", "[AEIMQUYcgkosw048]", "B04", "[AQgw]", 
        "B04S", "<B04> ?", "B16S", "<B16> ?", "B64S", "<B64> ?" };
    String[] arrayOfString4 = { 
        "boolean", "<_>(true|false|1|0)<_>", "decimal", "<_>([-+]?<d>+(\\.<d>+)?)<_>", "float", "<_>([-+]?<d>+(\\.<d>+)?([Ee][-+]?<d>+)?|INF|-INF|NaN)<_>", "integer", "<_>[-+]?[0-9]+<_>", "duration", "<_>(-?P(((<d>+Y)?(<d>+M)?(<d>+D)?(T(((<d>+H)?(<d>+M)?(<d>+(\\.<d>+)?S)?)&~()))?)&~()))<_>", 
        "dateTime", "<_>(-?<Y>-<M>-<D>T<T>(\\.<d>+)?<Z>?)<_>", "time", "<_>(<T>(\\.<d>+)?<Z>?)<_>", "date", "<_>(-?<Y>-<M>-<D><Z>?)<_>", "gYearMonth", "<_>(-?<Y>-<M><Z>?)<_>", "gYear", "<_>(-?<Y><Z>?)<_>", 
        "gMonthDay", "<_>(--<M>-<D><Z>?)<_>", "gDay", "<_>(--<D><Z>?)<_>", "gMonth", "<_>(--<M><Z>?)<_>", "hexBinary", "<_>([0-9a-fA-F]{2}*)<_>", "base64Binary", "<_>(((<B64S><B64S><B64S><B64S>)*((<B64S><B64S><B64S><B64>)|(<B64S><B64S><B16S>=)|(<B64S><B04S>= ?=)))?)<_>", 
        "language", "<_>[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*<_>", "nonPositiveInteger", "<_>(0+|-<d>+)<_>", "negativeInteger", "<_>(-[1-9]<d>*)<_>", "nonNegativeInteger", "<_>(<d>+)<_>", "positiveInteger", "<_>([1-9]<d>*)<_>" };
    System.out.println("Building XML Schema automata...");
    Map<?, ?> map2 = a(arrayOfString3);
    a(arrayOfString4, map2);
    a(map2, "UNSIGNEDLONG", Automaton.c("18446744073709551615"));
    a(map2, "UNSIGNEDINT", Automaton.c("4294967295"));
    a(map2, "UNSIGNEDSHORT", Automaton.c("65535"));
    a(map2, "UNSIGNEDBYTE", Automaton.c("255"));
    a(map2, "LONG", Automaton.c("9223372036854775807"));
    a(map2, "LONG_NEG", Automaton.c("9223372036854775808"));
    a(map2, "INT", Automaton.c("2147483647"));
    a(map2, "INT_NEG", Automaton.c("2147483648"));
    a(map2, "SHORT", Automaton.c("32767"));
    a(map2, "SHORT_NEG", Automaton.c("32768"));
    a(map2, "BYTE", Automaton.c("127"));
    a(map2, "BYTE_NEG", Automaton.c("128"));
    LinkedHashMap<Object, Object> linkedHashMap1 = new LinkedHashMap<>();
    linkedHashMap1.putAll(map1);
    linkedHashMap1.putAll(map2);
    String[] arrayOfString5 = { 
        "Nmtoken2", "<_><Nmtoken><_>", "Name2", "<_><Name><_>", "NCName2", "<_><NCName><_>", "QName2", "<_><QName><_>", "Nmtokens", "<_>(<Nmtoken><_>)+", 
        "NCNames", "<_>(<NCName><_>)+", "Names", "<_>(<Name><_>)+", "unsignedLong", "<_><UNSIGNEDLONG><_>", "unsignedInt", "<_><UNSIGNEDINT><_>", "unsignedShort", "<_><UNSIGNEDSHORT><_>", 
        "unsignedByte", "<_><UNSIGNEDBYTE><_>", "long", "<_>(<LONG>|-<LONG_NEG>)<_>", "int", "<_>(<INT>|-<INT_NEG>)<_>", "short", "<_>(<SHORT>|-<SHORT_NEG>)<_>", "byte", "<_>(<BYTE>|-<BYTE_NEG>)<_>", 
        "string", "<Char>*" };
    a(arrayOfString5, linkedHashMap1);
    System.out.println("Building Unicode block automata...");
    a(a, "BasicLatin", Automaton.a(false, ''));
    a(a, "Latin-1Supplement", Automaton.a('', 'ÿ'));
    a(a, "LatinExtended-A", Automaton.a('Ā', 'ſ'));
    a(a, "LatinExtended-B", Automaton.a('ƀ', 'ɏ'));
    a(a, "IPAExtensions", Automaton.a('ɐ', 'ʯ'));
    a(a, "SpacingModifierLetters", Automaton.a('ʰ', '˿'));
    a(a, "CombiningDiacriticalMarks", Automaton.a('̀', 'ͯ'));
    a(a, "Greek", Automaton.a('Ͱ', 'Ͽ'));
    a(a, "Cyrillic", Automaton.a('Ѐ', 'ӿ'));
    a(a, "Armenian", Automaton.a('԰', '֏'));
    a(a, "Hebrew", Automaton.a('֐', '׿'));
    a(a, "Arabic", Automaton.a('؀', 'ۿ'));
    a(a, "Syriac", Automaton.a('܀', 'ݏ'));
    a(a, "Thaana", Automaton.a('ހ', '޿'));
    a(a, "Devanagari", Automaton.a('ऀ', 'ॿ'));
    a(a, "Bengali", Automaton.a('ঀ', '৿'));
    a(a, "Gurmukhi", Automaton.a('਀', '੿'));
    a(a, "Gujarati", Automaton.a('઀', '૿'));
    a(a, "Oriya", Automaton.a('଀', '୿'));
    a(a, "Tamil", Automaton.a('஀', '௿'));
    a(a, "Telugu", Automaton.a('ఀ', '౿'));
    a(a, "Kannada", Automaton.a('ಀ', '೿'));
    a(a, "Malayalam", Automaton.a('ഀ', 'ൿ'));
    a(a, "Sinhala", Automaton.a('඀', '෿'));
    a(a, "Thai", Automaton.a('฀', '๿'));
    a(a, "Lao", Automaton.a('຀', '໿'));
    a(a, "Tibetan", Automaton.a('ༀ', '࿿'));
    a(a, "Myanmar", Automaton.a('က', '႟'));
    a(a, "Georgian", Automaton.a('Ⴀ', 'ჿ'));
    a(a, "HangulJamo", Automaton.a('ᄀ', 'ᇿ'));
    a(a, "Ethiopic", Automaton.a('ሀ', '፿'));
    a(a, "Cherokee", Automaton.a('Ꭰ', '᏿'));
    a(a, "UnifiedCanadianAboriginalSyllabics", Automaton.a('᐀', 'ᙿ'));
    a(a, "Ogham", Automaton.a(' ', '᚟'));
    a(a, "Runic", Automaton.a('ᚠ', '᛿'));
    a(a, "Khmer", Automaton.a('ក', '៿'));
    a(a, "Mongolian", Automaton.a('᠀', '᢯'));
    a(a, "LatinExtendedAdditional", Automaton.a('Ḁ', 'ỿ'));
    a(a, "GreekExtended", Automaton.a('ἀ', '῿'));
    a(a, "GeneralPunctuation", Automaton.a(' ', '⁯'));
    a(a, "SuperscriptsandSubscripts", Automaton.a('⁰', '₟'));
    a(a, "CurrencySymbols", Automaton.a('₠', '⃏'));
    a(a, "CombiningMarksforSymbols", Automaton.a('⃐', '⃿'));
    a(a, "LetterlikeSymbols", Automaton.a('℀', '⅏'));
    a(a, "NumberForms", Automaton.a('⅐', '↏'));
    a(a, "Arrows", Automaton.a('←', '⇿'));
    a(a, "MathematicalOperators", Automaton.a('∀', '⋿'));
    a(a, "MiscellaneousTechnical", Automaton.a('⌀', '⏿'));
    a(a, "ControlPictures", Automaton.a('␀', '␿'));
    a(a, "OpticalCharacterRecognition", Automaton.a('⑀', '⑟'));
    a(a, "EnclosedAlphanumerics", Automaton.a('①', '⓿'));
    a(a, "BoxDrawing", Automaton.a('─', '╿'));
    a(a, "BlockElements", Automaton.a('▀', '▟'));
    a(a, "GeometricShapes", Automaton.a('■', '◿'));
    a(a, "MiscellaneousSymbols", Automaton.a('☀', '⛿'));
    a(a, "Dingbats", Automaton.a('✀', '➿'));
    a(a, "BraillePatterns", Automaton.a('⠀', '⣿'));
    a(a, "CJKRadicalsSupplement", Automaton.a('⺀', '⻿'));
    a(a, "KangxiRadicals", Automaton.a('⼀', '⿟'));
    a(a, "IdeographicDescriptionCharacters", Automaton.a('⿰', '⿿'));
    a(a, "CJKSymbolsandPunctuation", Automaton.a('　', '〿'));
    a(a, "Hiragana", Automaton.a('぀', 'ゟ'));
    a(a, "Katakana", Automaton.a('゠', 'ヿ'));
    a(a, "Bopomofo", Automaton.a('㄀', 'ㄯ'));
    a(a, "HangulCompatibilityJamo", Automaton.a('㄰', '㆏'));
    a(a, "Kanbun", Automaton.a('㆐', '㆟'));
    a(a, "BopomofoExtended", Automaton.a('ㆠ', 'ㆿ'));
    a(a, "EnclosedCJKLettersandMonths", Automaton.a('㈀', '㋿'));
    a(a, "CJKCompatibility", Automaton.a('㌀', '㏿'));
    a(a, "CJKUnifiedIdeographsExtensionA", Automaton.a('㐀', '䶵'));
    a(a, "CJKUnifiedIdeographs", Automaton.a('一', '鿿'));
    a(a, "YiSyllables", Automaton.a('ꀀ', '꒏'));
    a(a, "YiRadicals", Automaton.a('꒐', '꓏'));
    a(a, "HangulSyllables", Automaton.a('가', '힣'));
    a(a, "CJKCompatibilityIdeographs", Automaton.a('豈', '﫿'));
    a(a, "AlphabeticPresentationForms", Automaton.a('ﬀ', 'ﭏ'));
    a(a, "ArabicPresentationForms-A", Automaton.a('ﭐ', '﷿'));
    a(a, "CombiningHalfMarks", Automaton.a('︠', '︯'));
    a(a, "CJKCompatibilityForms", Automaton.a('︰', '﹏'));
    a(a, "SmallFormVariants", Automaton.a('﹐', '﹯'));
    a(a, "ArabicPresentationForms-B", Automaton.a('ﹰ', '﻾'));
    a(a, "Specials", Automaton.a('﻿', '﻿'));
    a(a, "HalfwidthandFullwidthForms", Automaton.a('＀', '￯'));
    a(a, "Specials", Automaton.a('￰', '�'));
    a(a, "OldItalic", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "Gothic", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "Deseret", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "ByzantineMusicalSymbols", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "MusicalSymbols", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "MathematicalAlphanumericSymbols", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "CJKUnifiedIdeographsExtensionB", Automaton.a('?', '?').a(Automaton.a('?', '?'))
        .e(Automaton.a('?').a(Automaton.a('?', '?'))));
    a(a, "CJKCompatibilityIdeographsSupplement", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "Tags", Automaton.a('?').a(Automaton.a('?', '?')));
    a(a, "PrivateUse", Automaton.a('', '')
        .e(Automaton.a('?', '?').a(Automaton.a('?', '?'))
          .e(Automaton.a('?').a(Automaton.a('?', '?'))))
        .e(Automaton.a('?', '?').a(Automaton.a('?', '?'))
          .e(Automaton.a('?').a(Automaton.a('?', '?')))));
    System.out.println("Building Unicode category automata...");
    LinkedHashMap<Object, Object> linkedHashMap2 = new LinkedHashMap<>();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("src/Unicode.txt"));
      try {
        StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
        streamTokenizer.resetSyntax();
        streamTokenizer.whitespaceChars(59, 59);
        streamTokenizer.whitespaceChars(10, 32);
        streamTokenizer.wordChars(48, 57);
        streamTokenizer.wordChars(97, 122);
        streamTokenizer.wordChars(65, 90);
        while (streamTokenizer.nextToken() != -1) {
          int i = Integer.parseInt(streamTokenizer.sval, 16);
          streamTokenizer.nextToken();
          String str1 = streamTokenizer.sval;
          Set<Integer> set = (Set)linkedHashMap2.get(str1);
          if (set == null) {
            set = new TreeSet();
            linkedHashMap2.put(str1, set);
          } 
          set.add(Integer.valueOf(i));
          String str2 = str1.substring(0, 1);
          set = (Set<Integer>)linkedHashMap2.get(str2);
          if (set == null) {
            set = new TreeSet<>();
            linkedHashMap2.put(str2, set);
          } 
          set.add(Integer.valueOf(i));
        } 
        bufferedReader.close();
      } catch (Throwable throwable) {
        try {
          bufferedReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    ArrayList<Automaton> arrayList = new ArrayList();
    for (Map.Entry<Object, Object> entry : linkedHashMap2.entrySet()) {
      ArrayList<Automaton> arrayList1 = new ArrayList();
      ArrayList<Automaton> arrayList2 = new ArrayList();
      for (Integer integer : entry.getValue()) {
        arrayList1.add(a(integer.intValue()));
        if (arrayList1.size() == 50) {
          arrayList2.add(Automaton.f(Automaton.a(arrayList1)));
          arrayList1.clear();
        } 
      } 
      arrayList2.add(Automaton.a(arrayList1));
      Automaton automaton1 = Automaton.f(Automaton.a(arrayList2));
      a(a, (String)entry.getKey(), automaton1);
      arrayList.add(automaton1);
    } 
    Automaton automaton = Automaton.f(((Automaton)a.get("Char")).y().c(Automaton.a(arrayList).G()));
    a(a, "Cn", automaton);
    a(a, "C", ((Automaton)a.get("C")).y().e(automaton));
  }
  
  private static Automaton a(int paramInt) {
    if (paramInt >= 65536) {
      paramInt -= 65536;
      char[] arrayOfChar = { (char)(55296 + (paramInt >> 10)), (char)(56320 + (paramInt & 0x3FF)) };
      return Automaton.b(new String(arrayOfChar));
    } 
    return Automaton.a((char)paramInt);
  }
  
  private static Map a(String[] paramArrayOfString) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    byte b = 0;
    while (b + 1 < paramArrayOfString.length)
      a(linkedHashMap, paramArrayOfString[b++], (new RegExp(paramArrayOfString[b++])).a(linkedHashMap)); 
    return linkedHashMap;
  }
  
  private static void a(String[] paramArrayOfString, Map paramMap) {
    byte b = 0;
    while (b + 1 < paramArrayOfString.length)
      a(a, paramArrayOfString[b++], (new RegExp(paramArrayOfString[b++])).a(paramMap)); 
  }
  
  private static void a(String paramString, Map paramMap) {
    a.put(paramString, (Automaton)paramMap.get(paramString));
  }
  
  private static void a(Map<String, Automaton> paramMap, String paramString, Automaton paramAutomaton) {
    paramMap.put(paramString, paramAutomaton);
    System.out.println("  " + paramString + ": " + paramAutomaton.r() + " states, " + paramAutomaton.s() + " transitions");
  }
  
  static Automaton a() {
    return b;
  }
}
