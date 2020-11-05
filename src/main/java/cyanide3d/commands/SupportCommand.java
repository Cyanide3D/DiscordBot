package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.utils.FilenamePatternResolver;

import java.io.IOException;

public class SupportCommand extends Command {
    public SupportCommand(){
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }



    @Override
    protected void execute(CommandEvent e) {
        IMOperation op = new IMOperation();
        op.addImage("wow.jpg");
        op.resize(957, 626);
        op.pointsize(20);
        op.font("Times-New-Roman");
        op.fill("Black");
        op.draw("text 30,30 'works like magic!'");
        op.addImage("outputs.jpg");
        ConvertCmd cmd = new ConvertCmd();
        cmd.setSearchPath("C:\\\\ImageMagick-7.0.10-Q16-HDRI");
        try {
            cmd.run(op);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
