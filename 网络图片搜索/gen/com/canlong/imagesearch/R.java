/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.canlong.imagesearch;

public final class R {
    public static final class attr {
        /** <p>Must be a string value, using '\\;' to escape characters such as '\\n' or '\\uxxxx' for a unicode character.
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int text=0x7f010000;
        /** <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int textColor=0x7f010001;
        /** <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int textSize=0x7f010002;
    }
    public static final class dimen {
        /**  Default screen margins, per the Android Design guidelines. 

         Customize dimensions originally defined in res/values/dimens.xml (such as
         screen margins) for sw720dp devices (e.g. 10" tablets) in landscape here.
    
         */
        public static final int activity_horizontal_margin=0x7f040000;
        public static final int activity_vertical_margin=0x7f040001;
    }
    public static final class drawable {
        public static final int ic_launcher=0x7f020000;
    }
    public static final class id {
        public static final int action_settings=0x7f080008;
        public static final int image_1=0x7f080006;
        public static final int image_2=0x7f080007;
        public static final int image_list=0x7f080003;
        public static final int key_word=0x7f080001;
        public static final int last_page=0x7f080004;
        public static final int mygallery=0x7f080000;
        public static final int next_page=0x7f080005;
        public static final int search_button=0x7f080002;
    }
    public static final class layout {
        public static final int activity_image_browse=0x7f030000;
        public static final int activity_main=0x7f030001;
        public static final int image_item_adapter=0x7f030002;
    }
    public static final class menu {
        public static final int image_browse=0x7f070000;
        public static final int main=0x7f070001;
    }
    public static final class string {
        public static final int action_settings=0x7f050001;
        public static final int app_name=0x7f050000;
        public static final int hello_world=0x7f050002;
        public static final int last_page=0x7f050005;
        public static final int next_page=0x7f050006;
        public static final int search=0x7f050003;
        public static final int title_activity_image_browse=0x7f050004;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.
    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f060000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f060001;
    }
    public static final class styleable {
        /** Attributes that can be used with a Gallery1.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #Gallery1_android_galleryItemBackground android:galleryItemBackground}</code></td><td></td></tr>
           </table>
           @see #Gallery1_android_galleryItemBackground
         */
        public static final int[] Gallery1 = {
            0x0101004c
        };
        /**
          <p>This symbol is the offset where the {@link android.R.attr#galleryItemBackground}
          attribute's value can be found in the {@link #Gallery1} array.
          @attr name android:galleryItemBackground
        */
        public static final int Gallery1_android_galleryItemBackground = 0;
        /** Attributes that can be used with a LabelView.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #LabelView_text com.canlong.imagesearch:text}</code></td><td></td></tr>
           <tr><td><code>{@link #LabelView_textColor com.canlong.imagesearch:textColor}</code></td><td></td></tr>
           <tr><td><code>{@link #LabelView_textSize com.canlong.imagesearch:textSize}</code></td><td></td></tr>
           </table>
           @see #LabelView_text
           @see #LabelView_textColor
           @see #LabelView_textSize
         */
        public static final int[] LabelView = {
            0x7f010000, 0x7f010001, 0x7f010002
        };
        /**
          <p>This symbol is the offset where the {@link com.canlong.imagesearch.R.attr#text}
          attribute's value can be found in the {@link #LabelView} array.


          <p>Must be a string value, using '\\;' to escape characters such as '\\n' or '\\uxxxx' for a unicode character.
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.canlong.imagesearch:text
        */
        public static final int LabelView_text = 0;
        /**
          <p>This symbol is the offset where the {@link com.canlong.imagesearch.R.attr#textColor}
          attribute's value can be found in the {@link #LabelView} array.


          <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.canlong.imagesearch:textColor
        */
        public static final int LabelView_textColor = 1;
        /**
          <p>This symbol is the offset where the {@link com.canlong.imagesearch.R.attr#textSize}
          attribute's value can be found in the {@link #LabelView} array.


          <p>Must be a dimension value, which is a floating point number appended with a unit such as "<code>14.5sp</code>".
Available units are: px (pixels), dp (density-independent pixels), sp (scaled pixels based on preferred font size),
in (inches), mm (millimeters).
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name com.canlong.imagesearch:textSize
        */
        public static final int LabelView_textSize = 2;
        /** Attributes that can be used with a TogglePrefAttrs.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #TogglePrefAttrs_android_preferenceLayoutChild android:preferenceLayoutChild}</code></td><td></td></tr>
           </table>
           @see #TogglePrefAttrs_android_preferenceLayoutChild
         */
        public static final int[] TogglePrefAttrs = {
            0x01010094
        };
        /**
          <p>This symbol is the offset where the {@link android.R.attr#preferenceLayoutChild}
          attribute's value can be found in the {@link #TogglePrefAttrs} array.
          @attr name android:preferenceLayoutChild
        */
        public static final int TogglePrefAttrs_android_preferenceLayoutChild = 0;
    };
}
