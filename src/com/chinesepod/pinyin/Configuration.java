/**
 * Copyright (C) 2013 ChinesePod 
 *
 * Author: Richard Mahn
 * 
 * Configuration and Data for this app
 */
package com.chinesepod.pinyin;

public class Configuration {
	public static final int SLIDE_STATUS_STUDYING = 1;
	public static final int SLIDE_STATUS_COMPLETE = 2;
	public static final int SLIDE_STATUS_NEW = 0;

	public static int mCurrentSlideIndex = 0;
	
	// URL for ChinesePod app
	public static final String CPOD_APP_URL = "market://details?id=com.chinesepod";
	// URL for ChinesePod site
	public static final String CPOD_SITE_URL = "http://chinesepod.com";
	
	// Selection for gender of audio
	public static final String AUDIO_GENDER_FEMALE = "f";
	public static final String AUDIO_GENDER_MALE = "m";

	// View types for reference table, 0 = show chart, 1 = Show List
	public static final int VIEW_TYPE_CHART = 0;
	public static final int VIEW_TYPE_LIST = 1;
	
	// Tabs
	public static final String TAB_LEARN = "Learn";
	public static final String TAB_REFERENCE = "Reference";
	
	public static final String PREFERENCES_NAVIGATION_MODE = "navigation_mode";
	
	public static String[][][] SLIDE_DATA = {
		{
			{
				"Introduction to Pinyin",
			},
			{
				"Introduction to Pinyin",
			},
			{
				"",
				"Pinyin is a system for writing standard Mandarin Chinese using the Roman alphabet. Pinyin was developed by the People's Republic of China in 1958, and implemented in 1979. It is used exclusively in mainland China to this day.",
			},
			{
				"",
				"Over the years pinyin has become widely accepted by the international community, replacing older systems of Romanization such as the Wade-Giles system."
			},
			{
				"",
				"It is important to remember that although pinyin uses the same letters as European languages, the sounds those letters represent are the sounds of Mandarin Chinese. Thus some letters may not make the sounds you expect. It is important that you pay close attention to how each letter of pinyin is pronounced, as you cannot read pinyin as if it were English."
			},
		},
		{
			{
				"A Finals with Easy Initials",
				"a, ai, ao, an, and ang with b, p, m, f, d, t, n, and l"
			},
			{
				"A Finals with Easy Initials",
			},
			{
				"",
				"The pinyin finals for this lesson have a has a rather uniform pronunciation in Chinese, very similar to the \"a\" sound in the English \"father\".",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin final sounds:",
				"a [ɑ]",
				"ai [ai]",
				"ao [ɑu]",
				"an [an]",
				"ang [ɑŋ]",
			},
			{
				"",
				"The following pinyin initials are very similar (if not identical) to their English equivalents and should not give you much trouble: b, p, m, f, d, t, n, l.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin initial sounds:",
				"b	[p]",
				"p	[ph]",
				"m	[ɑu]",
				"f	[f]",
				"d	[t]",
				"t	[th]",
				"n	[n]",
				"l	[l]",
			},
			{
				"Audio Chart",
				"<grid initials=none,b,p,m,f,d,t,n,l finals=a,ai,ao,an,ang>",
			},
		},
		{
			{
				"O Finals with Easy Initials",
				"o, ao, ong, and ou with b, p, m, f, d, t, n, and l"
			},
			{
				"O Finals with Easy Initials",
			},
			{
				"",
				"The pinyin 'o' combines exclusively (with no other sounds involved) with only four pure initial sounds: b, p, m, and f. In these cases the o does not make the same sound as it does when all by itself. In these cases, the o makes a sound which starts out sounding like the English word \"war\". When o combines with u to make ou, the sound is similar to the English word \"oh.\"",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin final sounds:",
				"o [o]",
				"-o	[uɔ]",
				"ao	[ɑu]",
				"-ong [uŋ]",
				"ou [ɤʊ]",
			},
			{
				"Audio Chart",
				"<grid initials=none,b,p,m,f,d,t,n,l finals=o,ong,ou>",
			},
		},
		{
			{
				"A and O Finals with Initials Z, C, and S",
				"a, ai, ao, an, ang, ong, ou with z, c, s",
			},
			{
				"A and O Finals with Initials Z, C, and S",
			},
			{
				"",
				"The z initial sound is almost like a \"dz\" sound. You can hear a very similar sound in English at the end of words like \"suds\", \"ads\", or \"kids\".",
			},
			{
				"",
				"The c initial sound is a \"ts\" sound. You can hear a very similar sound in English at the end of words like \"cuts\", \"hats\", or \"bits\".",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin initial sounds:",
				"z [ts]",
				"c [tsh]",
				"s [s]",
			},
			{
				"Audio Chart",
				"<grid initials=z,c,s finals=a,ai,ao,an,ang,ong,ou>",
			},
		},
		{
			{
				"A and O Initials with Finals ZH, CH, SH, and R",
				"a, ai, ao, an, ang, ong, ou with zh, chi, sh, r",
			},
			{
				"A and O Initials with Finals ZH, CH, SH, and R",
			},
			{
				"",
				"The pinyin initial sounds zh, ch, and sh are similar to English's \"j,\" \"ch,\" and \"sh\" sounds, but not exactly the same. The zh, ch, and sh sounds in Mandarin are all pronounced with the tongue in the same place, just as the sounds \"j\", \"ch\", and \"sh\" in English are all pronounced with the tongue in the same place.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin initial sounds:",
			 	"zh [ts]",			
		 		"ch [tsʰ]",		
		 		"sh [s]",	
		 		"r [ɻ] or [ʐ]",			
			},
			{
				"Audio Chart",
				"<grid initials=zh,ch,sh,r finals=a,ai,ao,an,ang,ong,ou>",
			},
		},
		{
			{
				"E Finals with Easy Initials",
				"e, ei, en, eng, er with b, p, m, f, d, t, n, and l",
			},
			{
				"E Finals with Easy Initials",
			},
			{
				"",
				"The pinyin final \"e\" is actually pronounced in several distinct ways, depending on what other sounds it is combined with. When e is a syllable all by itself, or when e simply combines with an initial initial, it sounds as if you started out with an [ʊ] sound (as in the English word \"put\"), and then ended with sort of an [ʌ] sound (as in the English slang word \"duh\".)",
			},
			{
				"",
				"For the -n and -ng final initials, the pronunciation of e stays roughly the same, with just the addition of the initial sounds at the end.",
			},
			{
				"",
				"The other sound that the pinyin e makes can be heard in ei. Mandarin's ei sounds like the \"ei\" in the English word \"vein\".",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin final sounds:",
				"e [ɣ]",
				"ei [ei]",
				"en [ən]",
				"eng [əŋ]",
				"er	[ɚ] or [er]",
			},
			{
				"Audio Chart",
				"<grid initials=none,b,p,m,f,d,t,n,l finals=e,ei,en,eng,er>",
			},
		},
		{
			{
				"E Finals with Initials Z, C, S, ZH, CH, SH, and R",
				"e, ei, en, eng, er with z, c, s, zh, ch, sh, r",
			},
			{
				"E Finals with Initials Z, C, S, ZH, CH, SH, and R",
			},
			{
				"",
				"The pronunciation of the pinyin sounds e, ei, en, and eng follow the same rules as the last lesson. However, that not every combination is used in Mandarin. (Those spaces are left blank in the chart.)",
			},
			{
				"Audio Chart",
				"<grid initials=z,c,s,zh,ch,sh,r finals=e,ei,en,eng,er>",
			},
		},
		{
			{
				"A, O, and E Finals with Initials G, K, and H",
				"a, ai, ao, an, ang, ong, ou, e, ei, en, eng with g, k, h"
			},
			{
				"A, O, and E Finals with Initials G, K, and H",
			},
			{
				"",
				"This lesson brings the familiar pinyin final sounds a, o, e with the new initial sounds g, k, and h. The pinyin g and k sounds may sometimes sound very similar to you. This is because both are unvoiced in Mandarin, meaning the vocal chords do not vibrate when you say them.",
			},
			{
				"",
				"The pinyin h initial, on the other hand, can seem harsher than the English \"h\" sound. This is because Mandarin speakers often constrict the back of their throat more than English speakers do, resulting in a raspier \"h\" sound similar to a Scottish \"ch\" sound (as in \"loch\") or a Hebrew \"ch\" sound (as in \"chutzpah\") as spoken by natives.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin initial sounds:",
				"g [k]",
 				"k [kh]",
 				"h [x]",
			},
			{
				"Audio Chart",
				"<grid initials=g,k,h finals=a,ai,ao,an,ang,ong,ou,e,ei,en,eng>",
			},
		},
		{
			{
				"I Finals with Easy Initials",
				"i, ia, iao, ie, iu, ian, iang, in, ing, iong with b, p, m, f, d, t, n, l",
			},
			{
				"I Finals with Easy Initials",
			},
			{
				"",
				"The pronunciation of the pinyin i final sound is largely uniform. The sound you must know is the solitary i final sound. It is written i when paired with an initial and written yi when not paired which it stands alone. It sounds like the English sound \"ee\" as in \"see\". This pronunciation holds true for all the syllables introduced in this lesson.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin final sounds:",
			 	"-i, yi [i]",
			 	"-ia, ya [ia]",
			 	"-iao, yao [iau]",
			 	"-ie, ye [iɛ]",
			 	"-iu, you [iou]",
			 	"-ian, yan [ian]",
			 	"-in, yin [in]",
			 	"-ing, ying [iŋ]",
			},
			{
				"Audio Chart",
				"<grid finals=i,ia,iao,ie,iu,ian,iang,in,ing,iong initials=none,b,p,m,f,d,t,n,l>",
			}
		},
		{
			{
				"I Final with Initials Z, C, S, ZH, CH, SH, and R",
				"i with z, c, s, zh, ch, sh, r",
			},
			{
				"I Final with Initials Z, C, S, ZH, CH, SH, and R",
			},
			{
				"",
				"Although none of the pinyin letters presented in this lesson are new, the pinyin initial sounds zh, ch, and sh, as well as the z, c, and s sounds combine with i to produce a slightly different sound from the basic \"ee\" sound of pinyin i that you learned.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's sounds:",
				"zi [tsɿ]",
				"ci [tsʰɿ]",
				"si [sɿ]",
				"zhi [tʂʅ]",
				"chi [tʂʰʅ]",
				"shi [ʂʅ]",
				"ri [ɻʅ]",
			},
			{
				"Audio Chart",
				"<grid initials=z,c,s,zh,ch,sh,r finals=i>",
			}
		},
		{
			{
				"I Finals with Initials J, Q, and X",
				"i, ia, iao, ie, iu, ian, iang, in, ing with j, q, x",
			},
			{
				"I Finals with Initials J, Q, and X",
			},
			{
				"",
				"You learned that the pinyin initial sounds zh, ch, and sh are similar to English's \"j,\" \"ch,\" and \"sh\" sounds. Then what about Mandarin's j, q, and x sounds? To the beginner, these sounds may also sound very similar to English's \"j,\" \"ch,\" and \"sh\" sounds, but there is in fact an important difference.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin sounds:",
				"ji [tɕ]",
				"qi [tɕʰ]",
				"xi [ɕ]",
			},
			{
				"Audio Chart",
				"<grid initials=j,q,x finals=i,ia,iao,ie,iu,ian,iang,in,ing>",
			},
		},
		{
			{
				"Non-Nasal U Finals with Easy Initials",
				"u, ua, uo, ui with b, p, m, f, d, t, n, l, z, c, s",
			},
			{
				"Non-Nasal U Finals with Easy Initials",
			},
			{
				"",
				"The basic \"u sound\" in Mandarin is u, and it is a simple \"oo\" sound. The lips are rounded and do not change shape as the sound is produced. When u is a syllable all by itself, it is written as wu.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin final sounds:",
				"u [u]",
				"ua [ua]",
				"uo [uɔ]",
				"ui [ueɪ]",
			},
			{
				"Audio Chart",
				"<grid initials=none,b,p,mf,d,t,n,z,c,s finals=u,ua,uo,ui>",
			},
		},
		{
			{
				"Nasal U Finals with Easy Initials",
				"uai, uan, un, uang, ueng with b, p, m, f, d, t, n, l, z, c, s",
			},
			{
				"Nasal U Finals with Easy Initials",
			},
			{
				"",
				"The pinyin initial sounds in this lesson are not new. The new sounds of this lesson are uai, uan, un, uang, and ueng.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson\'s pinyin final sounds:",
				"uai [uaɪ]",
				"uan [uan]",
				"un [uən]",
				"uang [uɑŋ]",
				"ueng [uɤŋ]",
			},
			{
				"Audio Chart",
				"<grid initials=none,b,p,mf,d,t,n,z,c,s finals=uai,uan,un,uang,ueng>",
			},
		},
		{
			{
				"U Finals with Initials ZH, CH, SH, and R",
				"u, ua, uo, ui, uai, uan, un, uang with zh, ch, sh",
			},
			{
				"U Finals with Initials ZH, CH, SH, and R",
			},
			{
				"",
				"\"Zh\", \"ch\", and \"sh\" are similar to English's \"j,\" \"ch,\" and \"sh\" sounds, but not exactly the same. The beginner will find this approximation perfectly serviceable, but the serious student will want to refine his or her pronunciation of these initials eventually.",
			},
			{
				"Audio Chart",
				"<grid initials=zh,ch,sh,r finals=u,ua,uo,ui,uai,uan,un,uang>",
			},
		},
		{
			{
				"U Finals with Initials G, K, and H",
				"u, ua, uo, ui, uai, uan, un, uang with g, k, h",
			},
			{
				"U Finals with Initials G, K, and H",
			},
			{
				"",
				"You've learned the pinyin g, k, and h initial sounds. In this lesson we apply the pinyin u sounds in a straightforward manner. Pay special attention to the proper pronunciation of finals uo, ui, and un.",
			},
			{
				"Audio Chart",
				"<grid initials=g,k,h finals=u,ua,uo,ui,uai,uan,un,uang>",
			},
		},
		{
			{
				"Ü Finals with Easy Initials",
				"ü, üe, üan, ün with b, p, m, f, d, t, n, l, z, c, s",
			},
			{
				"Ü Finals with Easy Initials",
			},
			{
				"",
				"The pinyin final sound ü is the most difficult final sound for most English speakers because this final sound does not exist in the English language (it does exist in German, however).",
			},
			{
				"",
				"To make the basic ü sound, make a continuous \"ee\" sound. As you make the sound, round your lips into the position they would need to be in to make an \"oo\" sound. By making the \"ee\" sound in your mouth with your lips in the \"oo\" position, you are pronouncing Mandarin's ü sound.",
			},
			{
				"",
				"The following are the IPA symbols for this lesson's pinyin finals sounds:",
				"ü [y]",
				"üe [yɛ]",
				"üan [yɛn]",
				"ün [yn]",
			},
			{
				"Audio Chart",
				"<grid initials=none,b,p,mf,d,t,n,z,c,s finals=ü,üe,üan,ün>",
			},
		},
		{
			{
				"Ü Finals with Initials J, Q, and X",
				"ü, üe, üan, ün with j, q, x",
			},
			{
				"Ü Finals with Initials J, Q, and X",
			},
			{
				"",
				"The umlaut (the two dots over the u) in the pinyin final sound ü are not always written in pinyin. This is true for all pinyin syllables beginning with \"yu,\" and it is also true for all the syllables presented in this lesson: j, q, and x.",
			},
			{
				"",
				"Dropping the two dots over ü does not cause confusion because the initials j, q, and x never combine with pinyin's basic u final. In fact, the only finals that j, q, and x ever combine with are i and ü. Knowing this will help you accurately recognize the syllables of spoken Mandarin.",
			},
			{
				"Audio Chart",
				"<grid initials=j,q,x finals=ü,üe,üan,ün>",
			},
		},
		{
			{
				"Tones",
				"1st, 2nd, 3rd and 4th tone",
			},
			{
				"Tones",
			},
			{
				"",
				"Mandarin Chinese is a tonal language. In order to differentiate meaning, the same syllable can be pronounced with different tones. Mandarin's tones give it a very distinctive quality, but the tones can also be a source of miscommunication if not given due attention.",
			},
			{
				"",
				"Mandarin is said to have four main tones and one neutral tone (or, as some say, five tones). Each tone has a distinctive pitch contour which can be graphed using the Chinese 5-level system.",
			},
			{
				"",
				"<img src=pinyin_tones>",
			},
		},
	};
	
	public static final String PINYIN_TABLE_DATA[][] = {
		{"","a","ai","ao","an","ang","o","ong","ou","e","ei","en","eng","er","i","ia","iao","ie","iu","io","iai","ian","iang","in","ing","iong","u","ua","uo","ui","uai","uan","uang","un","ueng","ü","üe","üan","ün"},
		{"","a","ai","ao","an","ang","o","","ou","ye","ei","en","eng","er","yi","ya","yao","ye","you","yo","yai","yan","yang","yin","ying","yong","wu","wa","wo","wei","wai","wan","wang","wen","weng","yu","yüe","yuan","yun"},
		{"b","ba","bai","bao","ban","bang","bo","","","","bei","ben","beng","","bi","","biao","bie","","","","bian","","bin","bing","","bu","","","","","","","","","","","",""},
		{"p","pa","pai","pao","pan","pang","po","","pou","","pei","pen","peng","","pi","","piao","pie","","","","pian","","pin","ping","","pu","","","","","","","","","","","",""},
		{"m","ma","mai","mao","man","mang","mo","","mou","me","mei","men","meng","","mi","","miao","mie","miu","","","mian","","min","ming","","mu","","","","","","","","","","","",""},
		{"f","fa","","","fan","fang","fo","","fou","","fei","fen","feng","","","","","","","","","","","","","","fu","","","","","","","","","","","",""},
		{"d","da","dai","dao","dan","dang","","dong","dou","de","dei","den","deng","","di","dia","diao","die","diu","","","dian","","","ding","","du","","duo","dui","","duan","","dun","","","","",""},
		{"t","ta","tai","tao","tan","tang","","tong","tou","te","tei","","teng","","ti","","tiao","tie","","","","tian","","","ting","","tu","","tuo","tui","","tuan","","tun","","","","",""},
		{"n","na","nai","nao","nan","nang","","nong","nou","ne","nei","nen","neng","","ni","","niao","nie","niu","","","nian","niang","nin","ning","","nu","","nuo","","","nuan","","nun","","nü","nüe","",""},
		{"l","la","lai","lao","lan","lang","lo","long","lou","le","lei","","leng","","li","lia","liao","lie","liu","","","lian","liang","lin","ling","","lu","","luo","","","luan","","lun","","lü","lüe","",""},
		{"g","ga","gai","gao","gan","gang","","gong","gou","ge","gei","gen","geng","","","","","","","","","","","","","","gu","gua","guo","gui","guai","guan","guang","gun","","","","",""},
		{"k","ka","kai","kao","kan","kang","","kong","kou","ke","","ken","keng","","","","","","","","","","","","","","ku","kua","kuo","kui","kuai","kuan","kuang","kun","","","","",""},
		{"h","ha","hai","hao","han","hang","","hong","hou","he","hei","hen","heng","","","","","","","","","","","","","","hu","hua","huo","hui","huai","huan","huang","hun","","","","",""},
		{"j","","","","","","","","","","","","","","ji","jia","jiao","jie","jiu","","","jian","jiang","jin","jing","jiong","","","","","","","","","","ju","jue","juan","jun"},
		{"q","","","","","","","","","","","","","","qi","qia","qiao","qie","qiu","","","qian","qiang","qin","qing","qiong","","","","","","","","","","qu","que","quan","qun"},
		{"x","","","","","","","","","","","","","","xi","xia","xiao","xie","xiu","","","xian","xiang","xin","xing","xiong","","","","","","","","","","xu","xue","xuan","xun"},
		{"zh","zha","zhai","zhao","zhan","zhang","","zhong","zhou","zhe","zhei","zhen","zheng","","zhi","","","","","","","","","","","","zhu","zhua","zhuo","zhui","zhuai","zhuan","zhuang","zhun","","","","",""},
		{"ch","cha","chai","chao","chan","chang","","chong","chou","che","","chen","cheng","","chi","","","","","","","","","","","","chu","chua","chuo","chui","chuai","chuan","chuang","chun","","","","",""},
		{"sh","sha","shai","shao","shan","shang","","","shou","she","shei","shen","sheng","","shi","","","","","","","","","","","","shu","shua","shuo","shui","shuai","shuan","shuang","shun","","","","",""},
		{"r","","","rao","ran","rang","","rong","rou","re","","ren","reng","","ri","","","","","","","","","","","","ru","rua","ruo","rui","","ruan","","run","","","","",""},
		{"z","za","zai","zao","zan","zang","","zong","zou","ze","zei","zen","zeng","","zi","","","","","","","","","","","","zu","","zuo","zui","","zuan","","zun","","","","",""},
		{"c","ca","cai","cao","can","cang","","cong","cou","ce","","cen","ceng","","ci","","","","","","","","","","","","cu","","cuo","cui","","cuan","","cun","","","","",""},
		{"s","sa","sai","sao","san","sang","","song","sou","se","","sen","seng","","si","","","","","","","","","","","","su","","suo","sui","","suan","","sun","","","","",""},
	};
	
	public static final String PINYIN_TABLE_TRANSPOSED_DATA[][] = {
		{"","","b","p","m","f","d","t","n","l","g","k","h","j","q","x","zh","ch","sh","r","z","c","s"},
		{"a","a","ba","pa","ma","fa","da","ta","na","la","ga","ka","ha","","","","zha","cha","sha","","za","ca","sa"},
		{"ai","ai","bai","pai","mai","","dai","tai","nai","lai","gai","kai","hai","","","","zhai","chai","shai","","zai","cai","sai"},
		{"ao","ao","bao","pao","mao","","dao","tao","nao","lao","gao","kao","hao","","","","zhao","chao","shao","rao","zao","cao","sao"},
		{"an","an","ban","pan","man","fan","dan","tan","nan","lan","gan","kan","han","","","","zhan","chan","shan","ran","zan","can","san"},
		{"ang","ang","bang","pang","mang","fang","dang","tang","nang","lang","gang","kang","hang","","","","zhang","chang","shang","rang","zang","cang","sang"},
		{"o","o","bo","po","mo","fo","","","","lo","","","","","","","","","","","","",""},
		{"ong","","","","","","dong","tong","nong","long","gong","kong","hong","","","","zhong","chong","","rong","zong","cong","song"},
		{"ou","ou","","pou","mou","fou","dou","tou","nou","lou","gou","kou","hou","","","","zhou","chou","shou","rou","zou","cou","sou"},
		{"e","ye","","","me","","de","te","ne","le","ge","ke","he","","","","zhe","che","she","re","ze","ce","se"},
		{"ei","ei","bei","pei","mei","fei","dei","tei","nei","lei","gei","","hei","","","","zhei","","shei","","zei","",""},
		{"en","en","ben","pen","men","fen","den","","nen","","gen","ken","hen","","","","zhen","chen","shen","ren","zen","cen","sen"},
		{"eng","eng","beng","peng","meng","feng","deng","teng","neng","leng","geng","keng","heng","","","","zheng","cheng","sheng","reng","zeng","ceng","seng"},
		{"er","er","","","","","","","","","","","","","","","","","","","","",""},
		{"i","yi","bi","pi","mi","","di","ti","ni","li","","","","ji","qi","xi","zhi","chi","shi","ri","zi","ci","si"},
		{"ia","ya","","","","","dia","","","lia","","","","jia","qia","xia","","","","","","",""},
		{"iao","yao","biao","piao","miao","","diao","tiao","niao","liao","","","","jiao","qiao","xiao","","","","","","",""},
		{"ie","ye","bie","pie","mie","","die","tie","nie","lie","","","","jie","qie","xie","","","","","","",""},
		{"iu","you","","","miu","","diu","","niu","liu","","","","jiu","qiu","xiu","","","","","","",""},
		{"io","yo","","","","","","","","","","","","","","","","","","","","",""},
		{"iai","yai","","","","","","","","","","","","","","","","","","","","",""},
		{"ian","yan","bian","pian","mian","","dian","tian","nian","lian","","","","jian","qian","xian","","","","","","",""},
		{"iang","yang","","","","","","","niang","liang","","","","jiang","qiang","xiang","","","","","","",""},
		{"in","yin","bin","pin","min","","","","nin","lin","","","","jin","qin","xin","","","","","","",""},
		{"ing","ying","bing","ping","ming","","ding","ting","ning","ling","","","","jing","qing","xing","","","","","","",""},
		{"iong","yong","","","","","","","","","","","","jiong","qiong","xiong","","","","","","",""},
		{"u","wu","bu","pu","mu","fu","du","tu","nu","lu","gu","ku","hu","","","","zhu","chu","shu","ru","zu","cu","su"},
		{"ua","wa","","","","","","","","","gua","kua","hua","","","","zhua","chua","shua","rua","","",""},
		{"uo","wo","","","","","duo","tuo","nuo","luo","guo","kuo","huo","","","","zhuo","chuo","shuo","ruo","zuo","cuo","suo"},
		{"ui","wei","","","","","dui","tui","","","gui","kui","hui","","","","zhui","chui","shui","rui","zui","cui","sui"},
		{"uai","wai","","","","","","","","","guai","kuai","huai","","","","zhuai","chuai","shuai","","","",""},
		{"uan","wan","","","","","duan","tuan","nuan","luan","guan","kuan","huan","","","","zhuan","chuan","shuan","ruan","zuan","cuan","suan"},
		{"uang","wang","","","","","","","","","guang","kuang","huang","","","","zhuang","chuang","shuang","","","",""},
		{"un","wen","","","","","dun","tun","nun","lun","gun","kun","hun","","","","zhun","chun","shun","run","zun","cun","sun"},
		{"ueng","weng","","","","","","","","","","","","","","","","","","","","",""},
		{"ü","yu","","","","","","","nü","lü","","","","ju","qu","xu","","","","","","",""},
		{"üe","yüe","","","","","","","nüe","lüe","","","","jue","que","xue","","","","","","",""},
		{"üan","yuan","","","","","","","","","","","","juan","quan","xuan","","","","","","",""},
		{"ün","yun","","","","","","","","","","","","jun","qun","xun","","","","","","",""},
	};
	
	private Configuration(){}
}
