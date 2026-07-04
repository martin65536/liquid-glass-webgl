package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n7 {
    public static final n7 e;
    public static final n7 f;
    public static final n7 g;
    public static final n7 h;
    public static final n7 i;
    public static final n7 j;
    public static final n7 k;
    public static final /* synthetic */ n7[] l;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, n7] */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.Enum, n7] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, n7] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, n7] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Enum, n7] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.Enum, n7] */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.lang.Enum, n7] */
    static {
        ?? r0 = new Enum("Paragraph", 0);
        e = r0;
        ?? r1 = new Enum("Span", 1);
        f = r1;
        ?? r3 = new Enum("VerbatimTts", 2);
        g = r3;
        ?? r5 = new Enum("Url", 3);
        h = r5;
        ?? r7 = new Enum("Link", 4);
        i = r7;
        ?? r9 = new Enum("Clickable", 5);
        j = r9;
        ?? r11 = new Enum("String", 6);
        k = r11;
        l = new n7[]{r0, r1, r3, r5, r7, r9, r11};
    }

    public static n7 valueOf(String str) {
        return (n7) Enum.valueOf(n7.class, str);
    }

    public static n7[] values() {
        return (n7[]) l.clone();
    }
}
